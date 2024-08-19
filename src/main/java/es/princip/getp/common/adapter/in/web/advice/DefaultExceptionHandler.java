package es.princip.getp.common.adapter.in.web.advice;

import es.princip.getp.common.adapter.in.web.dto.ApiErrorResponse;
import es.princip.getp.common.adapter.in.web.dto.ApiErrorResponse.ApiErrorResult;
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
    public ResponseEntity<ApiErrorResult> handleHttpRequestMethodNotSupportedException(
        final HttpRequestMethodNotSupportedException exception
    ) {
        log.info(exception.getMessage());
        return ApiErrorResponse.error(DefaultErrorCode.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResult> handleException(final Exception exception) {
        log.error(exception.getMessage(), exception);
        return ApiErrorResponse.error(DefaultErrorCode.INTERNAL_SERVER_ERROR);
    }
}