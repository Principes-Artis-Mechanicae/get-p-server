package es.princip.getp.domain.people.exception;

import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public class NotRegisteredPeopleProfileException extends BusinessLogicException {

    private static final HttpStatus status = HttpStatus.CONFLICT;
    private static final String code = "NOT_REGISTERED_PEOPLE_PROFILE";
    private static final String message = "프로젝트에 지원하려면 피플 프로필을 등록해야 합니다.";

    public NotRegisteredPeopleProfileException() {
        super(status, ErrorDescription.of(code, message));
    }
}
