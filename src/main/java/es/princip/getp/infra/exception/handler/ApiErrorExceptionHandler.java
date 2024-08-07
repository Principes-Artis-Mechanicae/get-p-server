package es.princip.getp.infra.exception.handler;

import es.princip.getp.infra.dto.response.ApiErrorResponse;
import es.princip.getp.infra.dto.response.ApiErrorResponse.ApiErrorResult;
import es.princip.getp.infra.exception.ApiErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(100)
@RestControllerAdvice
public class ApiErrorExceptionHandler {

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<ApiErrorResult> handleBusinessLogicException(final ApiErrorException exception) {
        log.debug("ApiErrorException: ", exception);
        return ApiErrorResponse.error(exception.getStatus(), exception.getDescription());
    }
}