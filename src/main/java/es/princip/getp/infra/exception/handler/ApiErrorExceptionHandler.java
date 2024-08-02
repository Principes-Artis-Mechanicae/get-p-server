package es.princip.getp.infra.exception.handler;

import es.princip.getp.infra.dto.response.ApiErrorResponse;
import es.princip.getp.infra.dto.response.ApiErrorResponse.ApiErrorResult;
import es.princip.getp.infra.exception.ApiErrorException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(100)
public class ApiErrorExceptionHandler {

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<ApiErrorResult> handleBusinessLogicException(final ApiErrorException exception) {
        return ApiErrorResponse.error(exception.getStatus(), exception.getDescription());
    }
}