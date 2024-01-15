package es.princip.getp.global.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {
    private HttpStatus status;
    private String code;
    private String message;

    public BusinessLogicException(ErrorCode errorCode) {
        super(errorCode.message());
        this.status = errorCode.status();
        this.code = errorCode.code();
        this.message = errorCode.message();
    }
}
