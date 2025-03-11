package es.princip.getp.application.project.apply.exception;

import es.princip.getp.application.support.ApplicationLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class AlreadyAppliedProjectException extends ApplicationLogicException {

    private static final String code = "ALREADY_APPLIED_PROJECT";
    private static final String message = "이미 지원한 프로젝트입니다.";

    public AlreadyAppliedProjectException() {
        super(ErrorDescription.of(code, message));
    }
}
