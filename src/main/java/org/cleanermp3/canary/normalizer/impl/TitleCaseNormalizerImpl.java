package org.cleanermp3.canary.normalizer.impl;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.log4j.Logger;
import org.cleanermp3.canary.Constants;
import org.cleanermp3.canary.exceptions.NormalizationException;
import org.cleanermp3.canary.normalizer.Normalizer;

/**
 * Created by daichi on 8/5/15.
 */
public class TitleCaseNormalizerImpl implements Normalizer {

    private static final Logger log = Logger.getLogger(Constants.LOG4J_LOGGER);
    @Override
    public String normalize(String raw, Constants.TagContents what) throws NormalizationException {
        if(raw == null || raw.length() == 0) {
            return Constants.UNKNOWN;
        }

        String processed =  WordUtils.capitalizeFully(raw);
        log.debug("Title Cased " + raw + " to " + processed);
        return processed;
    }
}
