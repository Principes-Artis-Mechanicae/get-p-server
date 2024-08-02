package es.princip.getp.domain.people.exception;

import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public class NotFoundPeopleException extends BusinessLogicException {

    private static final HttpStatus status = HttpStatus.NOT_FOUND;
    private static final String code = "NOT_FOUND_PEOPLE";
    private static final String message = "존재하지 않는 피플입니다.";

    public NotFoundPeopleException() {
        super(status, ErrorDescription.of(code, message));
    }
}
