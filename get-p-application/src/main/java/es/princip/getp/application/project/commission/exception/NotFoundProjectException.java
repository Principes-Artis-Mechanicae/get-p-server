package es.princip.getp.application.project.commission.exception;

import es.princip.getp.domain.support.ErrorDescription;
import es.princip.getp.domain.support.ErrorDescriptionException;

public class NotFoundProjectException extends ErrorDescriptionException {

    private static final String code = "NOT_FOUND_PROJECT";
    private static final String message = "존재하지 않는 프로젝트입니다.";

    public NotFoundProjectException() {
        super(ErrorDescription.of(code, message));
    }
}
