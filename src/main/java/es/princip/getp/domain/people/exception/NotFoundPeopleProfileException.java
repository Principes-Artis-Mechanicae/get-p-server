package es.princip.getp.domain.people.exception;

import es.princip.getp.infra.exception.ErrorDescription;
import es.princip.getp.infra.exception.NotFoundException;

public class NotFoundPeopleProfileException extends NotFoundException {

    private static final String code = "NOT_FOUND_PEOPLE_PROFILE";
    private static final String message = "존재하지 않는 피플 프로필입니다.";

    public NotFoundPeopleProfileException() {
        super(ErrorDescription.of(code, message));
    }
}
