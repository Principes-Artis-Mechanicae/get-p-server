package es.princip.getp.infra.exception.handler;

import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiErrorResult;
import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessLogicExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ApiErrorResult> handleBusinessLogicException(final BusinessLogicException exception) {
        if (exception.getMessage() != null) {
            final ErrorDescription description = ErrorDescription.of(
                "BAD_REQUEST",
                exception.getMessage()
            );
            return ApiResponse.error(HttpStatus.BAD_REQUEST, description);
        }
        return ApiResponse.error(exception.getErrorCode());
    }
}