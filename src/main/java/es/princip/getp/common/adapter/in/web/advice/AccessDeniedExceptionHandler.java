package es.princip.getp.common.adapter.in.web.advice;

import es.princip.getp.common.adapter.in.web.dto.ApiErrorResponse;
import es.princip.getp.common.adapter.in.web.dto.ApiErrorResponse.ApiErrorResult;
import es.princip.getp.common.exception.DefaultErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(100)
@RestControllerAdvice
public class AccessDeniedExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResult> handleAccessDeniedException(final AccessDeniedException exception) {
        log.info("AccessDeniedException: {}", exception.getMessage());
        return ApiErrorResponse.error(DefaultErrorCode.ACCESS_DENIED);
    }
}