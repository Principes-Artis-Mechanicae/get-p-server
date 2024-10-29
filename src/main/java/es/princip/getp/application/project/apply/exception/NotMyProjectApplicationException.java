package es.princip.getp.application.project.apply.exception;

import es.princip.getp.application.support.ForbiddenException;
import es.princip.getp.domain.support.ErrorDescription;

public class NotMyProjectApplicationException extends ForbiddenException {

    private static final String code = "NOT_MY_PROJECT_APPLICATION";
    private static final String message = "프로젝트 지원자 본인이 아닙니다.";

    public NotMyProjectApplicationException() {
        super(ErrorDescription.of(code, message));
    }
}
