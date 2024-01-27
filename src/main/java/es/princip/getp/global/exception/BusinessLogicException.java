package es.princip.getp.global.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {
    private final HttpStatus status;
    private final String code;
    private final String message;

    public BusinessLogicException(ErrorCode errorCode) {
        super(errorCode.message());
        this.status = errorCode.status();
        this.code = errorCode.code();
        this.message = errorCode.message();
    }
}
