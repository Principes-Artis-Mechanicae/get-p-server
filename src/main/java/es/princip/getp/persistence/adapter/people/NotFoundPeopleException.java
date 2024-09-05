package es.princip.getp.persistence.adapter.people;

import es.princip.getp.common.exception.ErrorDescription;
import es.princip.getp.common.exception.NotFoundException;

class NotFoundPeopleException extends NotFoundException {

    private static final String code = "NOT_FOUND_PEOPLE";
    private static final String message = "존재하지 않는 피플입니다.";

    public NotFoundPeopleException() {
        super(ErrorDescription.of(code, message));
    }
}
