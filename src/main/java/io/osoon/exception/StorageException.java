package io.osoon.exception;

/**
 * @author whiteship
 */
public class StorageException extends OSoonException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

}
