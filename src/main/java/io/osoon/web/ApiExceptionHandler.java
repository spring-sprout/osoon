package io.osoon.web;

import io.osoon.exception.ApiError;
import io.osoon.exception.OSoonException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

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
        ApiError apiError = ApiError.badRequest(ex.getLocalizedMessage());
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    // TODO And we can also add our own global custom exception handlers.

    @ExceptionHandler({ HttpServerErrorException.class, HttpClientErrorException.class})
    public ResponseEntity<ApiError> handleConstraintViolation(WebRequest request, HttpStatusCodeException ex) {
        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getMessage());
        apiError.setStatus(ex.getStatusCode());

        return new ResponseEntity<>(apiError, ex.getStatusCode());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleControllerException(HttpServletRequest request, Throwable ex) {
        return ResponseEntity.badRequest().body(ApiError.badRequest(ex.getLocalizedMessage()));
    }

    @ExceptionHandler(OSoonException.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleOSoonException(HttpServletRequest request, Throwable ex) {
        return ResponseEntity.badRequest().body(ApiError.badRequest(ex.getLocalizedMessage()));
    }
}
