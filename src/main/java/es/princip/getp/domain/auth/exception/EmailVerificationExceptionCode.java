package es.princip.getp.domain.auth.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.BusinessLogicExceptionCode;

public enum EmailVerificationExceptionCode implements BusinessLogicExceptionCode {
    EXPIRED_VERIFICATION_CODE(HttpStatus.FORBIDDEN, "만료된 인증 코드"), 
    INVALID_VERIFICATION(HttpStatus.FORBIDDEN, "유효하지 않은 인증"), 
    INCORRECT_VERIFICATION_CODE(HttpStatus.CONFLICT, "인증 번호 불일치"),
    ALREADY_VERIFICATION_CODE_SENDED(HttpStatus.FORBIDDEN, "사용자가 이메일 인증 유효 시간 내에 재요청"),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "사용자가 이미 가입된 이메일로 인증 요청"),
    AREADY_AUTHENTICATED_EMAIL(HttpStatus.CONFLICT, "사용자가 이미 인증된 이메일로 인증 요청");
    
    private final HttpStatus status;
    private final String message;
    
    EmailVerificationExceptionCode(HttpStatus status, String message) {
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
