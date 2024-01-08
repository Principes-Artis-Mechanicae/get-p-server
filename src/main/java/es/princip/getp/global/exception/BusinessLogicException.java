package es.princip.getp.global.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {
    private HttpStatus status;
    private String code;
    private String message;

    public BusinessLogicException(BusinessLogicExceptionCode exceptionCode) {
        super(exceptionCode.message());
        this.status = exceptionCode.status();
        this.code = exceptionCode.code();
        this.message = exceptionCode.message();
    }
}
