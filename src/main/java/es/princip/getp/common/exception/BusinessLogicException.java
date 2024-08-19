package es.princip.getp.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessLogicException extends ApiErrorException {

    private static final HttpStatus status = HttpStatus.CONFLICT;

    public BusinessLogicException(final ErrorDescription description) {
        super(status, description);
    }
}