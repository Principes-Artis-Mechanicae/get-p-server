package es.princip.getp.infra.exception.handler;

import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.dto.response.ApiResponse.ApiErrorResult;
import es.princip.getp.infra.exception.BusinessLogicException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessLogicExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ApiErrorResult> handleBusinessLogicException(final BusinessLogicException exception) {
        return ApiResponse.error(exception.getErrorCode());
    }
}