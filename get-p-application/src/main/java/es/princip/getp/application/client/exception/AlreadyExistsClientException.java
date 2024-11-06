package es.princip.getp.application.client.exception;

import es.princip.getp.application.support.ApplicationLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class AlreadyExistsClientException extends ApplicationLogicException {

    private static final String code = "ALREADY_EXISTS_CLIENT";
    private static final String message = "이미 의뢰자 정보를 등록했습니다.";

    public AlreadyExistsClientException() {
        super(ErrorDescription.of(code, message));
    }
}
