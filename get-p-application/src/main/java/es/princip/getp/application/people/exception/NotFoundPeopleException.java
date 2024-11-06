package es.princip.getp.application.people.exception;

import es.princip.getp.application.support.NotFoundException;
import es.princip.getp.domain.support.ErrorDescription;

import java.util.Collection;
import java.util.stream.Collectors;

class NotFoundPeopleException extends NotFoundException {

    private static final String code = "NOT_FOUND_PEOPLE";
    private static final String message = "존재하지 않는 피플입니다.";

    public NotFoundPeopleException() {
        super(ErrorDescription.of(code, message));
    }

    public NotFoundPeopleException(final String ids) {
        super(ErrorDescription.of(code, ids + "(은)는" + message));
    }

    public static NotFoundPeopleException from(final Collection<Long> peopleIds) {
        final String ids = peopleIds.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(", "));
        return new NotFoundPeopleException(ids);
    }
}
