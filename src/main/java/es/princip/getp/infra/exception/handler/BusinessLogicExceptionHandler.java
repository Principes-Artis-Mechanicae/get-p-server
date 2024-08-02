package es.princip.getp.infra.exception.handler;

import es.princip.getp.infra.dto.response.ApiErrorResponse;
import es.princip.getp.infra.dto.response.ApiErrorResponse.ApiErrorResult;
import es.princip.getp.infra.exception.BusinessLogicException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(100)
public class BusinessLogicExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ApiErrorResult> handleBusinessLogicException(final BusinessLogicException exception) {
        if (exception.getErrorCode() == null) {
            return ApiErrorResponse.error(exception.getStauts(), exception.getDescription());
        }
        return ApiErrorResponse.error(exception.getErrorCode());
    }
}