package es.princip.getp.application.project.meeting.exception;

import es.princip.getp.application.support.ForbiddenException;
import es.princip.getp.domain.support.ErrorDescription;

public class NotClientOfProjectException extends ForbiddenException {

    private static final String code = "NOT_MY_PROJECT";
    private static final String message = "해당 프로젝트의 의뢰자가 아닙니다.";

    public NotClientOfProjectException() {
        super(ErrorDescription.of(code, message));
    }
}
