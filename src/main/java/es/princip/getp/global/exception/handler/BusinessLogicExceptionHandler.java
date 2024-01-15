package es.princip.getp.global.exception.handler;

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
    public ResponseEntity<ApiErrorResult> businessLogicException(final BusinessLogicException businessLogicException) {
        HttpStatus status = businessLogicException.getStatus();
        String code = businessLogicException.getCode();
        String message = businessLogicException.getMessage();
        return ResponseEntity.status(status).body(ApiResponse.error(status, code, message));
    }
}