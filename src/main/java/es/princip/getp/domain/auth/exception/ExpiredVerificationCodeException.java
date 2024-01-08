package es.princip.getp.domain.auth.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.BusinessLogicException;

public class ExpiredVerificationCodeException extends BusinessLogicException {
    private static final HttpStatus status = HttpStatus.CONFLICT;
    private static final String code = "EXPIRED_VERIFICATION_CODE";
    private static final String message = "만료된 인증 코드";
    
    public ExpiredVerificationCodeException() {
        super(status, code, message);
    }
}