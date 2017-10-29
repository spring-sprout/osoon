package io.osoon.exception;

/**
 * @author whiteship
 */
public class OSoonException extends RuntimeException {

    public OSoonException(String message) {
        super(message);
    }

    public OSoonException(String message, Throwable cause) {
        super(message, cause);
    }

}
