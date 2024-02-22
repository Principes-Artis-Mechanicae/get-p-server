package es.princip.getp.domain.project.exception;

import es.princip.getp.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ProjectApplicationErrorCode implements ErrorCode {
    INVALID_EXPECTED_DURATION(HttpStatus.CONFLICT, "유효하지 않은 희망 작업 기간");

    private final HttpStatus status;
    private final String message;

    ProjectApplicationErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public String message() {
        return this.message;
    }

    public String code() {
        return this.name();
    }

    public HttpStatus status() {
        return this.status;
    }
}
