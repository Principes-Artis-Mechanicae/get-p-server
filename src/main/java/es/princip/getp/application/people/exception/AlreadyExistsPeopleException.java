package es.princip.getp.application.people.exception;

import es.princip.getp.domain.support.DomainLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class AlreadyExistsPeopleException extends DomainLogicException {

    private static final String code = "ALREADY_EXISTS_PEOPLE";
    private static final String message = "이미 피플 정보를 등록했습니다.";

    public AlreadyExistsPeopleException() {
        super(ErrorDescription.of(code, message));
    }
}
