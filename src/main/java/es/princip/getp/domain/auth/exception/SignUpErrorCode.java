package es.princip.getp.domain.auth.exception;

import es.princip.getp.infra.exception.ErrorCode;
import es.princip.getp.infra.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public enum SignUpErrorCode implements ErrorCode {
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "이미 가입된 이메일 주소"),
    NOT_AGREED_REQUIRED_SERVICE_TERM(HttpStatus.CONFLICT, "필수 서비스 약관 미동의"),
    NOT_VERIFIED_EMAIL(HttpStatus.CONFLICT, "이메일 인증 미실시");

    private final HttpStatus status;
    private final ErrorDescription description;

    SignUpErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.description = ErrorDescription.of(this.name(), message);
    }

    public HttpStatus status() {
        return status;
    }

    public ErrorDescription description() { return description; }
}
