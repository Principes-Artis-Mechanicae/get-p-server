package es.princip.getp.application.client.exception;

import es.princip.getp.common.exception.BusinessLogicException;
import es.princip.getp.common.exception.ErrorDescription;

public class AlreadyExistsClientException extends BusinessLogicException {

    private static final String code = "ALREADY_EXISTS_CLIENT";
    private static final String message = "이미 의뢰자 정보를 등록했습니다.";

    public AlreadyExistsClientException() {
        super(ErrorDescription.of(code, message));
    }
}
