package org.cleanermp3.canary.exceptions;

/**
 * Given as a base class for common methods of all Canary Exceptions. DO NOT USE
 * Created by daichi on 9/5/15.
 */

@Deprecated
public class CanaryBaseException extends Exception {

    private static String lineSep;
    static {
        lineSep = System.lineSeparator();
    }

    public CanaryBaseException () {
        super();
    }

    public CanaryBaseException(String message) {
        super(message);
    }

    public CanaryBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Generate entire stack trace as a {@code String}
     * @return Stack Trace as a string
     */
    @Override
    public String toString() {
        StackTraceElement[] st = getStackTrace();
        String message = getMessage();
        String exception = getClass().getCanonicalName();
        StringBuilder builder = new StringBuilder();
        Throwable cause;
        String causedBy = "Caused By";

        builder.append(exception).append(": ").append(message).append(lineSep);
        builder.append(convertToString(st)).append(lineSep);
        cause = getCause();
        while (cause != null){
            builder.append(causedBy)
                    .append(convertToString(cause.getStackTrace()))
                    .append(lineSep);
            cause = cause.getCause();
        }
        return builder.toString();
    }

    public String convertToString(StackTraceElement[] stack) {
        StringBuilder builder = new StringBuilder();
        String gap = "\t\t";
        for(StackTraceElement elem : stack) {
            builder.append(gap)
                    .append(elem.toString())
                    .append(lineSep);
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object anotherException) {
        return super.equals(anotherException);
    }

    @Override
    @Deprecated
    public int hashCode() {
        return super.hashCode();
    }
}
