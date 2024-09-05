package es.princip.getp.api.handler;

import es.princip.getp.api.support.dto.ApiErrorResponse;
import es.princip.getp.domain.support.ErrorDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(1)
@RestControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse.ApiErrorResult> handle(
        final MethodArgumentNotValidException exception
    ) {
        log.debug(exception.getMessage(), exception);
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