package io.osoon.exception;

/**
 * @author whiteship
 */
public class MeetingNotFoundException extends OSoonException {

    public MeetingNotFoundException(Long meetingId) {
        super("Meeting doesn't exist with id '" + meetingId + "'");
    }
}
