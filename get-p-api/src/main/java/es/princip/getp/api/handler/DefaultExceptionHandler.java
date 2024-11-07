package es.princip.getp.api.handler;

import es.princip.getp.api.support.dto.ApiErrorResponse;
import es.princip.getp.api.support.dto.ApiErrorResponse.ApiErrorResult;
import es.princip.getp.domain.support.ErrorDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(3)
@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResult> handle(final Exception exception) {
        log.error(exception.getMessage(), exception);
        final ErrorDescription description = ErrorDescription.of(
            "INTERNAL_SERVER_ERROR",
            "서버 내부 오류가 발생했습니다."
        );
        return ApiErrorResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, description);
    }
}