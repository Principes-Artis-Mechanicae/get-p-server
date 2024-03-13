package es.princip.getp.global.exception.handler;

import es.princip.getp.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import es.princip.getp.global.exception.BusinessLogicException;
import es.princip.getp.global.util.ApiResponse;
import es.princip.getp.global.util.ApiResponse.ApiErrorResult;

@RestControllerAdvice
public class BusinessLogicExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ApiErrorResult> businessLogicException(
        final BusinessLogicException businessLogicException) {
        ErrorCode errorCode = businessLogicException.getErrorCode();
        HttpStatus status = errorCode.status();
        return ResponseEntity.status(status).body(ApiResponse.error(errorCode));
    }
}