package es.princip.getp.api.handler;

import es.princip.getp.api.support.dto.ApiErrorResponse;
import es.princip.getp.api.support.dto.ApiErrorResponse.ApiErrorResult;
import es.princip.getp.domain.support.ErrorDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(1)
@RestControllerAdvice
public class AccessDeniedExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResult> handle(final AccessDeniedException exception) {
        log.debug(exception.getMessage(), exception);
        final ErrorDescription description = ErrorDescription.of(
            "ACCESS_DENIED",
            "접근 권한이 없습니다."
        );
        return ApiErrorResponse.error(HttpStatus.FORBIDDEN, description);
    }
}