package io.osoon.exception;

/**
 * @author whiteship
 */
public class UserNotFoundException extends OSoonException {

    public UserNotFoundException(long id) {
        super("Use not found with id '" + id + "'");
    }
}
