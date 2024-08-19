package es.princip.getp.domain.project.exception;

import es.princip.getp.common.exception.ErrorDescription;
import es.princip.getp.common.exception.ExternalApiErrorException;

public class FailedMeetingSendingException extends ExternalApiErrorException {

    private static final String code = "FAILED_MEETING_SENDING";
    private static final String message = "미팅 안내 전송에 실패했습니다. 잠시 후 다시 시도해주세요.";

    public FailedMeetingSendingException() {
        super(ErrorDescription.of(code, message));
    }
}
