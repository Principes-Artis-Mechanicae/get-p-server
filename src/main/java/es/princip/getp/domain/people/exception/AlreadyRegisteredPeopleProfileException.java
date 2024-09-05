package es.princip.getp.domain.people.exception;

import es.princip.getp.domain.support.DomainLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class AlreadyRegisteredPeopleProfileException extends DomainLogicException {

    private static final String code = "ALREADY_REGISTERED_PEOPLE_PROFILE";
    private static final String message = "이미 피플 프로필을 등록했습니다.";

    public AlreadyRegisteredPeopleProfileException() {
        super(ErrorDescription.of(code, message));
    }
}
