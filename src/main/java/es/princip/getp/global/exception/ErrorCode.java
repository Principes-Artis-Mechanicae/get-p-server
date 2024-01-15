package es.princip.getp.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    public HttpStatus status();
    public String code();
    public String message();
}