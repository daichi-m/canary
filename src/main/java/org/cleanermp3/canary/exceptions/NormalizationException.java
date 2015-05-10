package org.cleanermp3.canary.exceptions;

import org.cleanermp3.canary.Constants;

public class NormalizationException extends CanaryException {

    public NormalizationException() {
        super();
    }

    public  NormalizationException(String message) {
        super(message);
    }

    public NormalizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NormalizationException(Throwable cause) {
        super(Constants.EXCEPTION_DEFAULT_MSG, cause);
    }

}
