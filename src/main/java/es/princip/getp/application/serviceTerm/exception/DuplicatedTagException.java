package es.princip.getp.application.serviceTerm.exception;

import es.princip.getp.application.support.ApplicationLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class DuplicatedTagException extends ApplicationLogicException {

    private static final String code = "DUPLICATED_TAG";
    private static final String message = "중복된 태그입니다.";

    public DuplicatedTagException() {
        super(ErrorDescription.of(code, message));
    }
}
