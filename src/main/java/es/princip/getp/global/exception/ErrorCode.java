package es.princip.getp.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus status();
    ErrorDescription description();
}