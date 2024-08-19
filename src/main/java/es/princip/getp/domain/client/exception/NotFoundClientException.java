package es.princip.getp.domain.client.exception;

import es.princip.getp.common.exception.ErrorDescription;
import es.princip.getp.common.exception.NotFoundException;

public class NotFoundClientException extends NotFoundException {

    private static final String code = "NOT_FOUND_CLIENT";
    private static final String message = "존재하지 않는 의뢰자입니다.";

    public NotFoundClientException() {
        super(ErrorDescription.of(code, message));
    }
}
