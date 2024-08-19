package es.princip.getp.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class NotFoundException extends ApiErrorException {

    public NotFoundException(final ErrorDescription description) {
        super(HttpStatus.NOT_FOUND, description);
    }
}