package es.princip.getp.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ForbiddenException extends ApiErrorException {

    public ForbiddenException(final ErrorDescription description) {
        super(HttpStatus.FORBIDDEN, description);
    }
}