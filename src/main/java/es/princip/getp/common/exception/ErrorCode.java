package es.princip.getp.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus status();
    ErrorDescription description();
}