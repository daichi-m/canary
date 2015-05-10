package org.cleanermp3.canary.normalizer.impl;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.log4j.Logger;
import org.cleanermp3.canary.Constants;
import org.cleanermp3.canary.exceptions.NormalizationException;
import org.cleanermp3.canary.normalizer.Normalizer;

import java.util.*;

/**
 * Created by daichi on 8/5/15.
 */
public class ArtistNameNormalizerImpl implements Normalizer {

    List<String> artists;
    private static final Logger log = Logger.getLogger(Constants.LOG4J_LOGGER);

    public ArtistNameNormalizerImpl() {
        artists = new ArrayList<>();
    }
    @Override
    public String normalize(String raw, Constants.TagContents what)
            throws NormalizationException {

        if(raw == null || raw.length() == 0) {
            return Constants.UNKNOWN;
        }

        if(!what.equals(Constants.TagContents.ARTIST)) {
            return raw;
        }

        String artist = raw.toLowerCase();
        String ret = raw, logMsg = "";
        String adjacentArtist;
        int pos, top, bottom, dist;
        boolean changed = false;

        if(!artists.contains(raw)) {
            pos = Collections.binarySearch(artists, artist);
            if(pos >= 0) {
                ret = restoreCase(artist);
                logMsg = "Didn't change the artist";
            } else {
                pos = -pos - 1;
                top = Math.max(pos - 2, 0);
                bottom = Math.min(pos + 2, artists.size() - 1);
                for(int i=top; i<=bottom; i++) {
                    adjacentArtist = artists.get(i);
                    dist = hammingDistance(artist, adjacentArtist);
                    log.debug("Hamming distance between " + artist + " and "
                            + adjacentArtist + " = " + dist);
                    if(((double)dist/(double)artist.length())/artist.length() <= 0.2) {
                        logMsg = "Hamming distance is " + dist + ". Changing to";
                        ret = restoreCase(adjacentArtist);
                        changed = true;
                        break;
                    }
                }
                if(!changed) {
                    logMsg = "Didn't get a match. Adding to list";
                    artists.add(artist);
                    ret = restoreCase(artist);
                }
            }

        } else {
            ret = restoreCase(artist);
            logMsg = "Didn't change the artist";

        }
        log.debug(logMsg + ": " + ret);
        return ret;
    }

    private String restoreCase(String artist) {
        return WordUtils.capitalizeFully(artist);
    }

    public int hammingDistance(String str1, String str2) {
        int len1, len2, min, max;
        int dist = 0;
        len1 = str1.length();
        len2 = str2.length();

        min = (len1 < len2) ? len1 : len2;
        max = (len1 < len2) ? len2 : len1;
        for(int i=0; i<min; i++) {
            if(str1.charAt(i) != str2.charAt(i)) {
                dist++;
            }
        }
        dist += (max - min);
        return dist;
    }
}
