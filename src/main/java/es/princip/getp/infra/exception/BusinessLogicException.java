package es.princip.getp.infra.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessLogicException extends ApiErrorException {

    private static final HttpStatus status = HttpStatus.CONFLICT;

    @Deprecated
    private final ErrorCode errorCode;

    @Deprecated
    public BusinessLogicException(final ErrorCode errorCode) {
        super(status, errorCode.description());
        this.errorCode = errorCode;
    }

    @Deprecated
    public BusinessLogicException(final String message) {
        super(status, ErrorDescription.of("COMMON_ERROR", message));
        this.errorCode = null;
    }

    public BusinessLogicException(final ErrorDescription description) {
        super(status, description);
        this.errorCode = null;
    }
}