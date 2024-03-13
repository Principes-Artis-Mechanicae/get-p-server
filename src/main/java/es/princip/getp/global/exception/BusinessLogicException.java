package es.princip.getp.global.exception;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {
    private final ErrorCode errorCode;

    public BusinessLogicException(ErrorCode errorCode) {
        super(errorCode.description().message());
        this.errorCode = errorCode;
    }
}