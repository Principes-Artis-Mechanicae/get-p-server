package es.princip.getp.domain.project.exception;

import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public class NotFoundProjectException extends BusinessLogicException {

    private static final HttpStatus status = HttpStatus.NOT_FOUND;
    private static final String code = "NOT_FOUND_PROJECT";
    private static final String message = "존재하지 않는 프로젝트입니다.";

    public NotFoundProjectException() {
        super(status, ErrorDescription.of(code, message));
    }
}
