package es.princip.getp.domain.people.exception;

import es.princip.getp.domain.support.DomainLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class NotRegisteredPeopleProfileException extends DomainLogicException {

    private static final String code = "NOT_REGISTERED_PEOPLE_PROFILE";
    private static final String message = "프로필을 먼저 등록해주세요.";

    public NotRegisteredPeopleProfileException() {
        super(ErrorDescription.of(code, message));
    }
}
