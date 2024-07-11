package es.princip.getp.infra.exception.handler;

import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiErrorResult;
import es.princip.getp.infra.exception.ErrorDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AccessDeniedExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResult> validationException(
        final AccessDeniedException exception) {
        ErrorDescription description = ErrorDescription.of(
            "ACCESS_DENIED",
            "접근할 수 없습니다."
        );
        log.debug(exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ApiResponse.error(HttpStatus.FORBIDDEN, description));
    }
}