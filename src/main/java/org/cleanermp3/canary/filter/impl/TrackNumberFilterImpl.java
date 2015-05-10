package org.cleanermp3.canary.filter.impl;

import org.apache.log4j.Logger;
import org.cleanermp3.canary.Constants;
import org.cleanermp3.canary.exceptions.FilterException;
import org.cleanermp3.canary.filter.Filter;

import java.util.regex.Matcher;

/**
 * Created by daichi on 8/5/15.
 */
public class TrackNumberFilterImpl implements Filter {

    private static Logger logger = Logger.getLogger(Constants.LOG4J_LOGGER);
    @Override
    public String filter(String raw) throws FilterException {
        Matcher matcher = Constants.TRACK_NUMBER_PATTERN.matcher(raw);
        String processed = raw;
        String matGrp;
        logger.debug("Track Number Filtering: " + raw);
        try {
            while (matcher.find()) {
                matGrp = matcher.group();
                logger.debug("Matched and Removed: " + matGrp);
                processed = processed.replace(matGrp, Constants.EMPTY);
            }
            return processed.trim();
        } catch (Exception ex) {
            throw new FilterException(ex);
        }
    }
}
