package es.princip.getp.persistence.adapter.project.meeting;

import es.princip.getp.domain.support.ErrorDescription;
import es.princip.getp.persistence.support.NotFoundException;

class NotFoundProjectMeetingException extends NotFoundException {

    private static final String code = "NOT_FOUND_PROJECT_MEETING";
    private static final String message = "존재하지 않는 미팅입니다.";

    public NotFoundProjectMeetingException() {
        super(ErrorDescription.of(code, message));
    }
}
