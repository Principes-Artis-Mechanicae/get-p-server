package es.princip.getp.infra.exception;

import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public BusinessLogicException(final ErrorCode errorCode) {
        super(errorCode.description().message());
        this.errorCode = errorCode;
        this.message = null;
    }

    public BusinessLogicException(final String message) {
        super(message);
        this.errorCode = null;
        this.message = message;
    }
}
