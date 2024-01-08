package es.princip.getp.domain.auth.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.BusinessLogicException;

public class IncorrectVerificationCodeException extends BusinessLogicException {
    private static final HttpStatus status = HttpStatus.CONFLICT;
    private static final String code = "INCORRECT_VERIFICATION_CODE";
    private static final String message = "인증 번호 불일치";
    
    public IncorrectVerificationCodeException() {
        super(status, code, message);
    }
}