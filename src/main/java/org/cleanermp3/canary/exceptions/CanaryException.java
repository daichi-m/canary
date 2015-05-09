package org.cleanermp3.canary.exceptions;

/**
 * Created by daichi on 9/5/15.
 */
public class CanaryException extends CanaryBaseException {

    public CanaryException() {
        super();
    }

    public CanaryException(String message) {
        super(message);
    }

    public CanaryException(String message, Throwable cause) {
        super(message, cause);
    }
}
