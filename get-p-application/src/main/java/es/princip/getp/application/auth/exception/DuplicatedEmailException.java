package es.princip.getp.application.auth.exception;

import es.princip.getp.application.support.ApplicationLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class DuplicatedEmailException extends ApplicationLogicException {

    private static final String code = "DUPLICATED_EMAIL";
    private static final String message = "중복된 이메일입니다. 다른 이메일을 사용해주세요.";

    public DuplicatedEmailException() {
        super(ErrorDescription.of(code, message));
    }
}
