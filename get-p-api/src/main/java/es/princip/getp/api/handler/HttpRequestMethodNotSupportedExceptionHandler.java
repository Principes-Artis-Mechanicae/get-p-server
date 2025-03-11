package es.princip.getp.api.handler;

import es.princip.getp.api.support.dto.ApiErrorResponse;
import es.princip.getp.domain.support.ErrorDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(1)
@RestControllerAdvice
public class HttpRequestMethodNotSupportedExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse.ApiErrorResult> handle(
        final HttpRequestMethodNotSupportedException exception
    ) {
        log.debug(exception.getMessage(), exception);
        final ErrorDescription description = ErrorDescription.of(
            "METHOD_NOT_ALLOWED",
            "지원하지 않는 HTTP 메소드입니다."
        );
        return ApiErrorResponse.error(HttpStatus.METHOD_NOT_ALLOWED, description);
    }
}