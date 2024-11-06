package es.princip.getp.application.project.apply.exception;

import es.princip.getp.application.support.ForbiddenException;
import es.princip.getp.domain.support.ErrorDescription;

public class NotMyProjectException extends ForbiddenException {

    private static final String code = "NOT_MY_PROJECT";
    private static final String message = "해당 프로젝트의 의뢰자가 아닙니다.";

    public NotMyProjectException() {
        super(ErrorDescription.of(code, message));
    }
}
