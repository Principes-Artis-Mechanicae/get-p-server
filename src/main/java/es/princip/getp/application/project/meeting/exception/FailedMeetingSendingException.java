package es.princip.getp.application.project.meeting.exception;

import es.princip.getp.application.support.ExternalServerException;
import es.princip.getp.domain.support.ErrorDescription;

public class FailedMeetingSendingException extends ExternalServerException {

    private static final String code = "FAILED_MEETING_SENDING";
    private static final String message = "미팅 안내 전송에 실패했습니다. 잠시 후 다시 시도해주세요.";

    public FailedMeetingSendingException() {
        super(ErrorDescription.of(code, message));
    }
}
