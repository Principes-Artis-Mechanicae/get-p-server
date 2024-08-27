package es.princip.getp.application.project.apply.exception;

import es.princip.getp.common.exception.BusinessLogicException;
import es.princip.getp.common.exception.ErrorDescription;

public class AlreadyAppliedProjectException extends BusinessLogicException {

    private static final String code = "ALREADY_APPLIED_PROJECT";
    private static final String message = "이미 지원한 프로젝트입니다.";

    public AlreadyAppliedProjectException() {
        super(ErrorDescription.of(code, message));
    }
}
