package es.princip.getp.domain.serviceTerm.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.BusinessLogicException;

public class ServiceTermNotFoundException extends BusinessLogicException {
    private static final HttpStatus status = HttpStatus.NOT_FOUND;
    private static final String code = "SERVICE_TERM_NOT_FOUND";
    private static final String message = "존재하지 않는 서비스 약관";
    
    public ServiceTermNotFoundException() {
        super(status, code, message);
    }
}