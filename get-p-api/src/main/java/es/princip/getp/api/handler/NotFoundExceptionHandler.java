package es.princip.getp.api.handler;

import es.princip.getp.api.support.dto.ApiErrorResponse;
import es.princip.getp.api.support.dto.ApiErrorResponse.ApiErrorResult;
import es.princip.getp.application.support.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(1)
@RestControllerAdvice
public class NotFoundExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResult> handle(final NotFoundException exception) {
        log.debug(exception.getMessage(), exception);
        return ApiErrorResponse.error(HttpStatus.NOT_FOUND, exception.getDescription());
    }
}