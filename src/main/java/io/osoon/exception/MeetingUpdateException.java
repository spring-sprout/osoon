package io.osoon.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author whiteship
 */
public class MeetingUpdateException extends OSoonException {

    public MeetingUpdateException(String propertyName, Throwable cause) {
        super(propertyName + "을(를) 수정할 수 없습니다.", cause);
    }

}
