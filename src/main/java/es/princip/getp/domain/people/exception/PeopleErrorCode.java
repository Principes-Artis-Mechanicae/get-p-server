package es.princip.getp.domain.people.exception;

import es.princip.getp.global.exception.ErrorCode;
import es.princip.getp.global.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public enum PeopleErrorCode implements ErrorCode {
    PEOPLE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 피플을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final ErrorDescription description;

    PeopleErrorCode(HttpStatus status, String message) {
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
