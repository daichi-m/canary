package org.cleanermp3.canary.exceptions;

import org.cleanermp3.canary.Constants;

public class FilterException extends CanaryException {

    public FilterException() {
        super();
    }

    public FilterException(String message) {
        super(message);
    }

    public FilterException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilterException(Throwable cause) {
        super(Constants.EXCEPTION_DEFAULT_MSG, cause);
    }

}
