package es.princip.getp.infra.exception.handler;

import es.princip.getp.infra.dto.response.ApiErrorResponse;
import es.princip.getp.infra.dto.response.ApiErrorResponse.ApiErrorResult;
import es.princip.getp.infra.exception.ErrorDescription;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(100)
public class MethodArgumentNotValidExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResult> handleMethodNotValidException(
        final MethodArgumentNotValidException exception
    ) {
        final ErrorDescription[] descriptions = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .map((FieldError fieldError) -> ErrorDescription.of(
                "WRONG_" + convertToSnakeCase(fieldError.getField()),
                fieldError.getDefaultMessage()
            ))
            .toArray(ErrorDescription[]::new);

        return ApiErrorResponse.error(HttpStatus.BAD_REQUEST, descriptions);
    }

    private static String convertToSnakeCase(String camelCase) {
        return camelCase.replaceAll("\\.", "_")
            .replaceAll("([a-z0-9])([A-Z])", "$1_$2")
            .toUpperCase();
    }
}