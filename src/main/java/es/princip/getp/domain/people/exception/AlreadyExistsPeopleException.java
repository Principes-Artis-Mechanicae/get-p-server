package es.princip.getp.domain.people.exception;

import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;

public class AlreadyExistsPeopleException extends BusinessLogicException {

    private static final String code = "ALREADY_EXISTS_PEOPLE";
    private static final String message = "이미 피플 정보를 등록했습니다.";

    public AlreadyExistsPeopleException() {
        super(ErrorDescription.of(code, message));
    }
}
