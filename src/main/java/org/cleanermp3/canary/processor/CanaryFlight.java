package org.cleanermp3.canary.processor;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.mpatric.mp3agic.*;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.cleanermp3.canary.Constants;
import org.cleanermp3.canary.exceptions.CanaryException;
import org.cleanermp3.canary.filter.Filter;
import org.cleanermp3.canary.filter.FilterFactory;
import org.cleanermp3.canary.normalizer.Normalizer;
import org.cleanermp3.canary.normalizer.NormalizerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Stack;

/**
 * Created by daichi on 7/5/15.
 */
public class CanaryFlight {

    private NormalizerFactory normalizerFactory;
    private FilterFactory filterFactory;
    private String musicHome;
    private List<Filter> filters;
    private List<Normalizer> normalizers;
    private Stack<File> directoryStack;
    private static  final Logger log = Logger.getLogger(Constants.LOG4J_LOGGER);

    @Inject
    public CanaryFlight(NormalizerFactory normalizerFactory,
                        FilterFactory filterFactory,
                        @Named(Constants.MUSIC_DIR_TAG)String musicHome,
                        Stack<File> directoryStack) {
        this.normalizerFactory = normalizerFactory;
        this.filterFactory = filterFactory;
        this.musicHome = musicHome;
        this.directoryStack = directoryStack;
    }

    public void populateFiltersAndNormalizers() {
        this.filters = filterFactory.getAvailableFilters();
        this.normalizers = normalizerFactory.getAvailableNormalizers();
    }

    public void flap() throws CanaryException {
        populateFiltersAndNormalizers();
        try {
            File baseDir = getBaseDir();
            directoryStack.push(baseDir);

            while (!directoryStack.isEmpty()) {
                File dir = directoryStack.pop();
                for(File file : dir.listFiles()) {
                    detectAndProcessFile(file);
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

    public void detectAndProcessFile(File file) throws IOException, CanaryException {

        Tika tika = new Tika();
        if(file.isDirectory()) {
            directoryStack.push(file);
            return;
        }
        String mediaType = tika.detect(file);
        log.info("File: " + file + " Media Type: " + mediaType);
        if(!mediaType.equals(Constants.MP3_MEDIA_TYPE)) {
            return;
        }
         processMp3File(file);
    }

    public void processMp3File(File file) throws IOException, CanaryException {

        Mp3File mp3file;
        ID3v2 v2Tag;
        ID3v1 v1Tag;
        try {
            mp3file = new Mp3File(file);
            if(mp3file.hasId3v2Tag()) {

            } else if(mp3file.hasId3v1Tag()) {

            } else {
                throw new CanaryException("Could not find ID3v1 and ID3v2 tag in " + file);
            }
        } catch (UnsupportedTagException | InvalidDataException e){
            System.err.println("Something went wrong with " + file + ": " + e);
        }
    }

    public ID3v1 convertV2ToV1(ID3v2 src) {
        return null;
    }

    public ID3v2 convertV1ToV2(ID3v1 src) {
        return null;
    }
}
