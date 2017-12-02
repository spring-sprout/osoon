package io.osoon.exception;

import org.springframework.http.HttpStatus;

/**
 * @author whiteship
 */
public class MeetingPermissionException extends OSoonException {

    public MeetingPermissionException(String userName, String permission, HttpStatus httpStatus) {
        super(userName + " 님은 " + permission + " 할 수 있는 권한이 없습니다.", httpStatus);
    }
}
