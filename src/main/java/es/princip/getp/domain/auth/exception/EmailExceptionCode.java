package es.princip.getp.domain.auth.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.BusinessLogicExceptionCode;

public enum EmailExceptionCode implements BusinessLogicExceptionCode {
    WRONG_EMAIL(HttpStatus.BAD_REQUEST, "잘못된 이메일 주소"),
    EMAIL_SERVER_UNAVAILABLE(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 서버 문제로 인해 전송 실패");

    private final HttpStatus status;
    private final String message;

    EmailExceptionCode(HttpStatus status, String message) {
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
