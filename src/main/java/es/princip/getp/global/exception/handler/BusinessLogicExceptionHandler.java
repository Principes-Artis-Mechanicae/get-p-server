package es.princip.getp.global.exception.handler;

import es.princip.getp.global.exception.BusinessLogicException;
import es.princip.getp.global.exception.ErrorCode;
import es.princip.getp.global.util.ApiResponse;
import es.princip.getp.global.util.ApiResponse.ApiErrorResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessLogicExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ApiErrorResult> validationException(
        final BusinessLogicException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.status(errorCode.status())
            .body(ApiResponse.error(errorCode.status(), errorCode.description()));
    }
}