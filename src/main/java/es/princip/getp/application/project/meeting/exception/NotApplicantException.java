package es.princip.getp.application.project.meeting.exception;

import es.princip.getp.application.support.ApplicationLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class NotApplicantException extends ApplicationLogicException {

    private static final String code = "NOT_APPLICANT";
    private static final String message = "해당 프로젝트의 지원자가 아닙니다.";

    public NotApplicantException() {
        super(ErrorDescription.of(code, message));
    }
}
