package es.princip.getp.api.exception;

import es.princip.getp.api.controller.common.dto.ApiErrorResponse;
import es.princip.getp.common.exception.DefaultErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse.ApiErrorResult> handleHttpRequestMethodNotSupportedException(
        final HttpRequestMethodNotSupportedException exception
    ) {
        log.info(exception.getMessage());
        return ApiErrorResponse.error(DefaultErrorCode.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse.ApiErrorResult> handleException(final Exception exception) {
        log.error(exception.getMessage(), exception);
        return ApiErrorResponse.error(DefaultErrorCode.INTERNAL_SERVER_ERROR);
    }
}