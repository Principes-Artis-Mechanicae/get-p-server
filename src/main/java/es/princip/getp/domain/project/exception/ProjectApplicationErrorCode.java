package es.princip.getp.domain.project.exception;

import es.princip.getp.global.exception.ErrorCode;
import es.princip.getp.global.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public enum ProjectApplicationErrorCode implements ErrorCode {
    INVALID_EXPECTED_DURATION(HttpStatus.CONFLICT, "유효하지 않은 희망 작업 기간");

    private final HttpStatus status;
    private final ErrorDescription description;

    ProjectApplicationErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.description = ErrorDescription.of(this.name(), message);
    }

    public HttpStatus status() {
        return status;
    }

    public ErrorDescription description() {
        return description;
    }
}
