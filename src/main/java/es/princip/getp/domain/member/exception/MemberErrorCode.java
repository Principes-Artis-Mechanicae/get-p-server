package es.princip.getp.domain.member.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.ErrorCode;

public enum MemberErrorCode implements ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원");

    private final HttpStatus status;
    private final String message;

    MemberErrorCode(HttpStatus status, String message) {
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
