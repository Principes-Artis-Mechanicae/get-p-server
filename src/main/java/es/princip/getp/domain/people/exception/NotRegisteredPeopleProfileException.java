package es.princip.getp.domain.people.exception;

import es.princip.getp.common.exception.BusinessLogicException;
import es.princip.getp.common.exception.ErrorDescription;

public class NotRegisteredPeopleProfileException extends BusinessLogicException {

    private static final String code = "NOT_REGISTERED_PEOPLE_PROFILE";
    private static final String message = "프로필을 먼저 등록해주세요.";

    public NotRegisteredPeopleProfileException() {
        super(ErrorDescription.of(code, message));
    }
}
