package es.princip.getp.domain.auth.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.BusinessLogicException;

public class AlreadyVerifiedEmailException extends BusinessLogicException {
    private static final HttpStatus status = HttpStatus.CONFLICT;
    private static final String code = "ALREADY_VERIFIED_EMAIL";
    private static final String message = "사용자가 이미 인증된 이메일로 인증 요청";
    
    public AlreadyVerifiedEmailException() {
        super(status, code, message);
    }
}
