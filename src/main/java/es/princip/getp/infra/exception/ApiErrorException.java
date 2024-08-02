package es.princip.getp.infra.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ApiErrorException extends RuntimeException {

    private final HttpStatus status;
    private final ErrorDescription description;

    public ApiErrorException(final HttpStatus status, final ErrorDescription description) {
        super(description.message());
        this.status = status;
        this.description = description;
    }
}