package es.princip.getp.domain.project.exception;

import es.princip.getp.common.exception.BusinessLogicException;
import es.princip.getp.common.exception.ErrorDescription;

public class NotApplicantException extends BusinessLogicException {

    private static final String code = "NOT_APPLICANT";
    private static final String message = "해당 프로젝트의 지원자가 아닙니다.";

    public NotApplicantException() {
        super(ErrorDescription.of(code, message));
    }
}
