package es.princip.getp.domain.auth.exception;

import es.princip.getp.global.exception.ErrorDescription;
import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.ErrorCode;

public enum EmailVerificationErrorCode implements ErrorCode {
    INVALID_EMAIL_VERIFICATION(HttpStatus.NOT_FOUND, "유효하지 않은 이메일 인증"),
    INCORRECT_EMAIL_VERIFICATION_CODE(HttpStatus.CONFLICT, "이메일 인증 코드 불일치"),
    ALREADY_EMAIL_VERIFICATION_CODE_SENDED(HttpStatus.CONFLICT, "사용자가 이메일 인증 코드 유효 시간 내에 재요청"),
    ALREADY_VERIFIED_EMAIL(HttpStatus.CONFLICT, "사용자가 이메일 인증 유효 시간 내에 재요청");

    private final HttpStatus status;
    private final ErrorDescription description;

    EmailVerificationErrorCode(HttpStatus status, String message) {
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