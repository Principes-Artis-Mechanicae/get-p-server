package es.princip.getp.domain.project.commission.exception;

import es.princip.getp.domain.support.DomainLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class EndedApplicationDurationException extends DomainLogicException {

    private static final String code = "ENDED_APPLICATION_DURATION";
    private static final String message = "지원자 모집 기간은 금일부터거나 이후여야 합니다.";

    public EndedApplicationDurationException() {
        super(ErrorDescription.of(code, message));
    }
}
