package es.princip.getp.domain.auth.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.BusinessLogicException;

public class NotVerifiedEmailException extends BusinessLogicException {
    private static final HttpStatus status = HttpStatus.CONFLICT;
    private static final String code = "NOT_VERIFIED_EMAIL";
    private static final String message = "이메일 인증 미실시";
    
    public NotVerifiedEmailException() {
        super(status, code, message);
    }
}
