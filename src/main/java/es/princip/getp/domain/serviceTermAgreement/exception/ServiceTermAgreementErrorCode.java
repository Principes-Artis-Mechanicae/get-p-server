package es.princip.getp.domain.serviceTermAgreement.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.ErrorCode;

public enum ServiceTermAgreementErrorCode implements ErrorCode {
    NOT_AGREED_REQUIRED_SERVICE_TERM(HttpStatus.CONFLICT, "필수 서비스 약관 미동의");

    private final HttpStatus status;
    private final String message;

    ServiceTermAgreementErrorCode(HttpStatus status, String message) {
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
