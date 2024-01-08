package es.princip.getp.domain.auth.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.BusinessLogicException;

public class WrongEmailException extends BusinessLogicException {
    private static final HttpStatus status = HttpStatus.BAD_REQUEST;
    private static final String code = "WRONG_EMAIL";
    private static final String message = "잘못된 이메일 주소";
    
    public WrongEmailException() {
        super(status, code, message);
    }
}
