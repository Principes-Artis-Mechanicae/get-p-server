package es.princip.getp.domain.auth.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.ErrorCode;

public enum EmailVerificationErrorCode implements ErrorCode {
    EXPIRED_EMAIL_VERIFICATION_CODE(HttpStatus.FORBIDDEN, "만료된 이메일 인증 코드"), 
    INVALID_EMAIL_VERIFICATION(HttpStatus.FORBIDDEN, "유효하지 않은 이메일 인증"), 
    INCORRECT_EMAIL_VERIFICATION_CODE(HttpStatus.CONFLICT, "이메일 인증 코드 불일치"),
    ALREADY_EMAIL_VERIFICATION_CODE_SENDED(HttpStatus.FORBIDDEN, "사용자가 이메일 인증 코드 유효 시간 내에 재요청"),
    ALREADY_VERIFIED_EMAIL(HttpStatus.CONFLICT, "사용자가 이메일 인증 유효 시간 내에 재요청");
    
    private final HttpStatus status;
    private final String message;
    
    EmailVerificationErrorCode(HttpStatus status, String message) {
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