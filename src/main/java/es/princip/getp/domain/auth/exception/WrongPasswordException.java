package es.princip.getp.domain.auth.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.BusinessLogicException;

public class WrongPasswordException extends BusinessLogicException {
    private static final HttpStatus status = HttpStatus.BAD_REQUEST;
    private static final String code = "WRONG_PASSWORD";
    private static final String message = "잘못된 비밀번호 형식";
    
    public WrongPasswordException() {
        super(status, code, message);
    }
}
