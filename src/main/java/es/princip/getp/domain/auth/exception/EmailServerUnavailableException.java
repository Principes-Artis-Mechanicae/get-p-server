package es.princip.getp.domain.auth.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.BusinessLogicException;

public class EmailServerUnavailableException extends BusinessLogicException{
    private static final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final String code = "EMAIL_SERVER_UNAVAILABLE";
    private static final String message = "이메일 서버 문제로 인해 전송 실패";
    
    public EmailServerUnavailableException() {
        super(status, code, message);
    }
}
