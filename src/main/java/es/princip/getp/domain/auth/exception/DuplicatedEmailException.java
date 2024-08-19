package es.princip.getp.domain.auth.exception;

import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;

public class DuplicatedEmailException extends BusinessLogicException {

    private static final String code = "DUPLICATED_EMAIL";
    private static final String message = "중복된 이메일입니다. 다른 이메일을 사용해주세요.";

    public DuplicatedEmailException() {
        super(ErrorDescription.of(code, message));
    }
}
