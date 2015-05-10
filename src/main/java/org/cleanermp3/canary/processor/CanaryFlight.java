package org.cleanermp3.canary.processor;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.mpatric.mp3agic.*;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.cleanermp3.canary.Constants;
import org.cleanermp3.canary.exceptions.CanaryException;
import org.cleanermp3.canary.exceptions.FilterException;
import org.cleanermp3.canary.exceptions.NormalizationException;
import org.cleanermp3.canary.filter.Filter;
import org.cleanermp3.canary.filter.FilterFactory;
import org.cleanermp3.canary.normalizer.Normalizer;
import org.cleanermp3.canary.normalizer.NormalizerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

/**
 * Created by daichi on 7/5/15.
 */
public class CanaryFlight {

    private NormalizerFactory normalizerFactory;
    private FilterFactory filterFactory;
    private String musicHome;
    private String musicDest;
    private List<Filter> filters;
    private List<Normalizer> normalizers;
    private Stack<File> directoryStack;
    private static  final Logger log = Logger.getLogger(Constants.LOG4J_LOGGER);

    @Inject
    public CanaryFlight(NormalizerFactory normalizerFactory,
                        FilterFactory filterFactory,
                        @Named(Constants.MUSIC_DIR_TAG)String musicHome,
                        @Named(Constants.MUSIC_DEST_TAG) String musicDest,
                        Stack<File> directoryStack) {
        this.normalizerFactory = normalizerFactory;
        this.filterFactory = filterFactory;
        this.musicHome = musicHome;
        this.musicDest = musicDest;
        this.directoryStack = directoryStack;
    }

    public void populateFiltersAndNormalizers() {
        this.filters = filterFactory.getAvailableFilters();
        this.normalizers = normalizerFactory.getAvailableNormalizers();
    }

    public void flap() throws  CanaryException {
        populateFiltersAndNormalizers();
        try {
            File baseDir = getBaseDir();
            createDestDir();
            directoryStack.push(baseDir);

            while (!directoryStack.isEmpty()) {
                File dir = directoryStack.pop();
                for(File file : dir.listFiles()) {
                    try {
                        detectAndProcessFile(file);
                    } catch (CanaryException ex) {
                        log.error(ex);
                    }
                }
            }

        } catch (IOException e) {
            throw new CanaryException(e.getMessage(), e);
        }
    }

    private File getBaseDir() throws IOException {
        File file = new File(musicHome);
        if(!(file.exists() && file.isDirectory())) {
            throw new IOException(musicHome + " does not exists or is not a directory");
        } else if(!(file.canRead() && file.canWrite() && file.canExecute())) {
            throw new IOException("Insufficient priviledge in " + musicHome);
        } else {
            return file;
        }
    }

    public void detectAndProcessFile(File file)
            throws IOException, CanaryException {

        Tika tika = new Tika();
        if(file.isDirectory()) {
            directoryStack.push(file);
            return;
        }
        String mediaType = tika.detect(file);
        //log.info("File: " + file + " Media Type: " + mediaType);
        if(!mediaType.equals(Constants.MP3_MEDIA_TYPE)) {
            return;
        }
        log.info("Processing file: " + file);
        processMp3File(file);
    }

    public void processMp3File(File file)
            throws IOException, CanaryException {

        Mp3File mp3file;
        ID3v2 v2Tag;
        ID3v1 v1Tag;
        String artist, title, album;
        try {
            mp3file = new Mp3File(file);
            if(mp3file.hasId3v2Tag()) {

                log.debug("Found ID3v2 Tag");
                v2Tag = mp3file.getId3v2Tag();

                // Title
                title = v2Tag.getTitle();
                title = applyFilters(title);
                title = normalize(title, Constants.TagContents.TITLE);
                v2Tag.setTitle(title);

                // Artist
                artist = v2Tag.getArtist();
                artist = applyFilters(artist);
                artist = normalize(artist, Constants.TagContents.ARTIST);
                v2Tag.setArtist(artist);

                // Album
                album = v2Tag.getAlbum();
                album = applyFilters(album);
                album = normalize(album, Constants.TagContents.ALBUM);
                v2Tag.setAlbum(album);

                v1Tag = convertV2ToV1(v2Tag);
                mp3file.setId3v1Tag(v1Tag);
                mp3file.setId3v2Tag(v2Tag);

            } else if(mp3file.hasId3v1Tag()) {

                log.debug("Found ID3v2 Tag");
                v1Tag = mp3file.getId3v1Tag();

                // Title
                title = v1Tag.getTitle();
                title = applyFilters(title);
                title = normalize(title, Constants.TagContents.TITLE);
                v1Tag.setTitle(title);

                // Artist
                artist = v1Tag.getArtist();
                artist = applyFilters(artist);
                artist = normalize(artist, Constants.TagContents.ARTIST);
                v1Tag.setArtist(artist);

                // Album
                album = v1Tag.getAlbum();
                album = applyFilters(album);
                album = normalize(album, Constants.TagContents.ALBUM);
                v1Tag.setAlbum(album);

                v2Tag = convertV1ToV2(v1Tag);
                mp3file.setId3v1Tag(v1Tag);
                mp3file.setId3v2Tag(v2Tag);

            } else {
                throw new CanaryException("Could not find ID3v1 and ID3v2 tag in " + file);
            }

            String dest = file.getAbsolutePath();
            dest = dest.replace(this.musicHome, this.musicDest);
            mp3file.save(dest);
        } catch (UnsupportedTagException | InvalidDataException | NotSupportedException e){
            System.err.println("Something went wrong with " + file + ": " + e);
        }
    }

    public ID3v1 convertV2ToV1(ID3v2 src) {

        ID3v1 dest = new ID3v1Tag();
        dest.setTitle(src.getTitle());
        dest.setAlbum(src.getAlbum());
        dest.setArtist(src.getArtist());
        return dest;
    }

    public ID3v2 convertV1ToV2(ID3v1 src) {
        ID3v2 dest = new ID3v24Tag();
        dest.setTitle(src.getTitle());
        dest.setAlbum(src.getAlbum());
        dest.setArtist(src.getArtist());
        return dest;
    }

    public String applyFilters(String raw)
            throws FilterException{
        String processed = raw;

        for(Filter f : filters) {
            processed = f.filter(processed);
        }
        return processed;
    }

    public String normalize(String raw, Constants.TagContents what)
            throws NormalizationException {
        String processed = raw;

        for(Normalizer n : normalizers) {
            processed = n.normalize(processed, what);
        }
        return processed;
    }

    public void createDestDir() {

        File file = new File(musicDest);
        if(file.exists())
            file.delete();

        file.mkdirs();
    }
}
