package es.princip.getp.domain.people.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.BusinessLogicException;

public class NotFoundException extends BusinessLogicException{
    private static final HttpStatus status = HttpStatus.NOT_FOUND;
    private static final String code = "NOTFOUND_DATA";
    private static final String message = "피플을 찾을 수 없습니다.";

    public NotFoundException() {
        super(status, code, message);
    }
}
