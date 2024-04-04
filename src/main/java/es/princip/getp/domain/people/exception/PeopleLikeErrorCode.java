package es.princip.getp.domain.people.exception;

import org.springframework.http.HttpStatus;

import es.princip.getp.global.exception.ErrorCode;
import es.princip.getp.global.exception.ErrorDescription;

public enum PeopleLikeErrorCode implements ErrorCode {
    ALREADY_LIKED(HttpStatus.CONFLICT, "이미 좋아요를 누른 프로 젝트입니다.");

    private final HttpStatus status;
    private final ErrorDescription description;

    PeopleLikeErrorCode(HttpStatus status, String message) {
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