package es.princip.getp.domain.client.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.ErrorCode;

public enum ClientErrorCode implements ErrorCode{
    CLIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "등록되지 않은 의뢰자 정보");

    private final HttpStatus status;
    private final String message;

    ClientErrorCode(HttpStatus status, String message) {
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