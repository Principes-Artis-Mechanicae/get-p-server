package es.princip.getp.domain.people.exception;

import es.princip.getp.infra.exception.ErrorCode;
import es.princip.getp.infra.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public enum PeopleProfileErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "피플 프로필을 찾을 수 없습니다."),
    ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 등록한 피플 프로필이 존재합니다.");

    private final HttpStatus status;
    private final ErrorDescription description;

    PeopleProfileErrorCode(HttpStatus status, String message) {
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
