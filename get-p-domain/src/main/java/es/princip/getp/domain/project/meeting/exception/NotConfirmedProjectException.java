package es.princip.getp.domain.project.meeting.exception;

import es.princip.getp.domain.support.DomainLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class NotConfirmedProjectException extends DomainLogicException {

    private static final String code = "NOT_CONFIRMED_PROJECT";
    private static final String message = "미팅이 완료되지 않은 프로젝트는 확정할 수 없습니다.";

    public NotConfirmedProjectException() {
        super(ErrorDescription.of(code, message));
    }
}
