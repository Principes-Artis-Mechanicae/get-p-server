package es.princip.getp.domain.auth.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.BusinessLogicException;

public class InvalidVerificationException extends BusinessLogicException {
    private static final HttpStatus status = HttpStatus.FORBIDDEN;
    private static final String code = "INVALID_VERIFICATION";
    private static final String message = "유효하지 않은 인증";
    
    public InvalidVerificationException() {
        super(status, code, message);
    }
}