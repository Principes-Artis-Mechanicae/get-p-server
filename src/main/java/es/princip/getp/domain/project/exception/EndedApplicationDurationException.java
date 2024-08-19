package es.princip.getp.domain.project.exception;

import es.princip.getp.common.exception.BusinessLogicException;
import es.princip.getp.common.exception.ErrorDescription;

public class EndedApplicationDurationException extends BusinessLogicException {

    private static final String code = "ENDED_APPLICATION_DURATION";
    private static final String message = "지원자 모집 기간은 금일부터거나 이후여야 합니다.";

    public EndedApplicationDurationException() {
        super(ErrorDescription.of(code, message));
    }
}
