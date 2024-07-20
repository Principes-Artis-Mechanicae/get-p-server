package es.princip.getp.infra.exception.handler;

import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiErrorResult;
import es.princip.getp.infra.exception.DefaultErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResult> handleHttpRequestMethodNotSupportedException(
        final HttpRequestMethodNotSupportedException exception
    ) {
        log.info(exception.getMessage());
        return ApiResponse.error(DefaultErrorCode.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResult> handleException(final Exception exception) {
        log.info(exception.getMessage());
        return ApiResponse.error(DefaultErrorCode.INTERNAL_SERVER_ERROR);
    }
}