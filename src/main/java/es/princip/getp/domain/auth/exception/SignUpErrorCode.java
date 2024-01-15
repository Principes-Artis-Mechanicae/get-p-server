package es.princip.getp.domain.auth.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.ErrorCode;

public enum SignUpErrorCode implements ErrorCode {
    WRONG_EMAIL(HttpStatus.BAD_REQUEST, "잘못된 이메일 주소"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 형식"),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 가입된 이메일 주소"),
    NOT_VERIFIED_EMAIL(HttpStatus.CONFLICT, "이메일 인증 미실시");

    private final HttpStatus status;
    private final String message;

    SignUpErrorCode(HttpStatus status, String message) {
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
