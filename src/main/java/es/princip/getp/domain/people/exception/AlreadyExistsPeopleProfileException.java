package es.princip.getp.domain.people.exception;

import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public class AlreadyExistsPeopleProfileException extends BusinessLogicException {

    private static final HttpStatus status = HttpStatus.CONFLICT;
    private static final String code = "ALREADY_EXISTS_PEOPLE_PROFILE";
    private static final String message = "이미 피플 프로필을 등록했습니다.";

    public AlreadyExistsPeopleProfileException() {
        super(status, ErrorDescription.of(code, message));
    }
}
