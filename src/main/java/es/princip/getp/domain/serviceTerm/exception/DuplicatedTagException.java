package es.princip.getp.domain.serviceTerm.exception;

import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;

public class DuplicatedTagException extends BusinessLogicException {

    private static final String code = "DUPLICATED_TAG";
    private static final String message = "중복된 태그입니다.";

    public DuplicatedTagException() {
        super(ErrorDescription.of(code, message));
    }
}