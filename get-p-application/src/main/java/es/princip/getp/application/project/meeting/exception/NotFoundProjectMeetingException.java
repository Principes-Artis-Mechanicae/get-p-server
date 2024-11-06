package es.princip.getp.application.project.meeting.exception;

import es.princip.getp.domain.support.ErrorDescription;
import es.princip.getp.domain.support.ErrorDescriptionException;

public class NotFoundProjectMeetingException extends ErrorDescriptionException {

    private static final String code = "NOT_FOUND_PROJECT_MEETING";
    private static final String message = "존재하지 않는 미팅입니다.";

    public NotFoundProjectMeetingException() {
        super(ErrorDescription.of(code, message));
    }
}
