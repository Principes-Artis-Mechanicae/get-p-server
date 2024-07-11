package es.princip.getp.infra.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus status();
    ErrorDescription description();
}