package es.princip.getp.global.exception;

import org.springframework.http.HttpStatus;

public enum BadRequestErrorCode implements ErrorCode {
    WRONG_FILE_URI("잘못된 파일 URI");

    private final HttpStatus status = HttpStatus.BAD_REQUEST;
    private final String message;

    BadRequestErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String message() {
        return this.message;
    }

    @Override
    public String code() {
        return this.name();
    }

    @Override
    public HttpStatus status() {
        return this.status;
    }
}