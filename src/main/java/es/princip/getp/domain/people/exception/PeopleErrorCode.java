package es.princip.getp.domain.people.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.ErrorCode;

public enum PeopleErrorCode implements ErrorCode{
    PEOPLE_NOT_FOUND(HttpStatus.NOT_FOUND, "피플을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

    PeopleErrorCode(HttpStatus status, String message) {
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
