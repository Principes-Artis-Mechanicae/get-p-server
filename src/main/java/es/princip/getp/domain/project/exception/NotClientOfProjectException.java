package es.princip.getp.domain.project.exception;

import es.princip.getp.common.exception.ErrorDescription;
import es.princip.getp.common.exception.ForbiddenException;

public class NotClientOfProjectException extends ForbiddenException {

    private static final String code = "NOT_MY_PROJECT";
    private static final String message = "해당 프로젝트의 의뢰자가 아닙니다.";

    public NotClientOfProjectException() {
        super(ErrorDescription.of(code, message));
    }
}
