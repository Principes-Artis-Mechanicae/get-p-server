package es.princip.getp.infra.exception.handler;

import es.princip.getp.infra.dto.response.ApiErrorResponse;
import es.princip.getp.infra.dto.response.ApiErrorResponse.ApiErrorResult;
import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(100)
public class BusinessLogicExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ApiErrorResult> handleBusinessLogicException(final BusinessLogicException exception) {
        if (exception.getMessage() != null) {
            final ErrorDescription description = ErrorDescription.of(
                "CONFLICT",
                exception.getMessage()
            );
            return ApiErrorResponse.error(HttpStatus.CONFLICT, description);
        }
        return ApiErrorResponse.error(exception.getErrorCode());
    }
}