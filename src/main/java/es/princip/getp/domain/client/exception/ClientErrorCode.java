package es.princip.getp.domain.client.exception;

import es.princip.getp.infra.exception.ErrorCode;
import es.princip.getp.infra.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public enum ClientErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 의뢰자 정보를 찾을 수 없습니다."),
    ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 등록한 의뢰자 정보가 존재합니다.");

    private final HttpStatus status;
    private final ErrorDescription description;

    ClientErrorCode(HttpStatus status, String message) {
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