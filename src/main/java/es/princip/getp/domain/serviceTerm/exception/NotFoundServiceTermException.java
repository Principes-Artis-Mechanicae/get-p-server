package es.princip.getp.domain.serviceTerm.exception;

import es.princip.getp.infra.exception.ErrorDescription;
import es.princip.getp.infra.exception.NotFoundException;

public class NotFoundServiceTermException extends NotFoundException {

    private static final String code = "NOT_FOUND_SERVICE_TERM";
    private static final String message = "존재하지 않는 서비스 약관입니다.";

    public NotFoundServiceTermException() {
        super(ErrorDescription.of(code, message));
    }
}
