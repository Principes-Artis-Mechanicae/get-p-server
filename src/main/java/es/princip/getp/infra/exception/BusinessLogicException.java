package es.princip.getp.infra.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessLogicException extends RuntimeException {
    private final HttpStatus stauts;

    @Deprecated
    private final ErrorCode errorCode;

    private final ErrorDescription description;

    public BusinessLogicException(final ErrorCode errorCode) {
        super(errorCode.description().message());
        this.stauts = errorCode.status();
        this.errorCode = errorCode;
        this.description = errorCode.description();
    }

    public BusinessLogicException(final String message) {
        super(message);
        this.stauts = HttpStatus.CONFLICT;
        this.errorCode = null;
        this.description = ErrorDescription.of("COMMON_ERROR", message);
    }

    public BusinessLogicException(final HttpStatus status, final ErrorDescription description) {
        super(description.message());
        this.stauts = status;
        this.errorCode = null;
        this.description = description;
    }
}