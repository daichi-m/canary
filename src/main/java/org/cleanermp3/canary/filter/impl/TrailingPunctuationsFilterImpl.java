package org.cleanermp3.canary.filter.impl;

import org.apache.log4j.Logger;
import org.cleanermp3.canary.Constants;
import org.cleanermp3.canary.exceptions.FilterException;
import org.cleanermp3.canary.filter.Filter;

import java.util.regex.Matcher;

/**
 * Created by daichi on 8/5/15.
 */
public class TrailingPunctuationsFilterImpl implements Filter {

    private static Logger logger = Logger.getLogger(Constants.LOG4J_LOGGER);
    @Override
    public String filter(String raw) throws FilterException {
        String processed = raw;
        String matGrp;
        Matcher matcher = Constants.BRACKET_PATTERN.matcher(raw);
        logger.debug("Filtering Trailing Punctuations: " + raw);

        try {
            // Remove trailing dangling brackets
            while (matcher.find()) {
                matGrp = matcher.group();
                logger.debug("Matched and Removed: " + matGrp);
                processed = processed.replace(matGrp, Constants.EMPTY);
            }
            processed = processed.trim();

            // Remove dangling - at the end
            matcher = Constants.TRAILING_PUNCT_PATTERN.matcher(processed);
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
