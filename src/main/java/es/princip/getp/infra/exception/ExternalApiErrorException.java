package es.princip.getp.infra.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ExternalApiErrorException extends ApiErrorException {

    public ExternalApiErrorException(final ErrorDescription description) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, description);
    }
}