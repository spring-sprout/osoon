package io.osoon.web.api;

import io.osoon.web.exception.ApiError;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author whiteship
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    // TODO In here, we can override existing exception handlers.

    /**
     * This exception handler will be used when @Valid annotated parameter failed validation.
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getLocalizedMessage());
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    // TODO And we can also add our own global custom exception handlers.

    @ExceptionHandler({ HttpServerErrorException.class, HttpClientErrorException.class})
    public ResponseEntity<Object> handleConstraintViolation(HttpStatusCodeException ex, WebRequest request) {
        ApiError apiError = new ApiError();
        apiError.setStatus(ex.getStatusCode());
        apiError.setMessage(ex.getLocalizedMessage());

        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
