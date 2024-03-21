package es.princip.getp.global.exception.handler;

import es.princip.getp.global.exception.ErrorDescription;
import es.princip.getp.global.util.ApiResponse;
import es.princip.getp.global.util.ApiResponse.ApiErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResult> validationException(
        final MethodArgumentNotValidException exception) {
        ErrorDescription[] descriptions = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .map((FieldError fieldError) -> ErrorDescription.of(
                "WRONG_" + convertToSnakeCase(fieldError.getField()),
                fieldError.getDefaultMessage()
            ))
            .toArray(ErrorDescription[]::new);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(HttpStatus.BAD_REQUEST, descriptions));
    }

    private static String convertToSnakeCase(String camelCase) {
        return camelCase.replaceAll("\\.", "_")
            .replaceAll("([a-z0-9])([A-Z])", "$1_$2")
            .toUpperCase();
    }
}