package es.princip.getp.domain.serviceTermAgreement.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.BusinessLogicException;

public class NotAgreedRequiredServiceTermException extends BusinessLogicException {
    private static final HttpStatus status = HttpStatus.CONFLICT;
    private static final String code = "NOT_AGREED_REQUIRED_SERVICE_TERM";
    private static final String message = "필수 서비스 약관 미동의";
    
    public NotAgreedRequiredServiceTermException() {
        super(status, code, message);
    }
}