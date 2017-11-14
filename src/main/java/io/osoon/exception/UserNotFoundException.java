package io.osoon.exception;

/**
 * @author whiteship
 */
public class UserNotFoundException extends OSoonException {

    public UserNotFoundException(long id) {
        super("User not found with id '" + id + "'");
    }
}
