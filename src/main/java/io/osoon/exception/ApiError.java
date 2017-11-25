package io.osoon.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author whiteship
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiError {

    HttpStatus status;

    String message;

    public ApiError(String message) {
        this.status = HttpStatus.BAD_REQUEST;
        this.message = message;
    }

}
