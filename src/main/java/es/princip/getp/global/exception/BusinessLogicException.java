package es.princip.getp.global.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {
    private HttpStatus status;
    private String code;
    private String message;

    public BusinessLogicException(HttpStatus status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
