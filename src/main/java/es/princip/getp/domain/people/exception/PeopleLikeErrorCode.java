package es.princip.getp.domain.people.exception;

import es.princip.getp.global.exception.ErrorCode;
import es.princip.getp.global.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public enum PeopleLikeErrorCode implements ErrorCode {
    ALREADY_LIKED(HttpStatus.CONFLICT, "이미 좋아요를 누른 피플입니다."),
    NEVER_LIKED(HttpStatus.NOT_FOUND, "해당 피플에게 좋아요를 누른 적이 없습니다.");

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