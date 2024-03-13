package es.princip.getp.domain.member.exception;

import es.princip.getp.global.exception.ErrorDescription;
import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.ErrorCode;

public enum MemberErrorCode implements ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원");

    private final HttpStatus status;
    private final ErrorDescription description;

    MemberErrorCode(HttpStatus status, String message) {
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
