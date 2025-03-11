package es.princip.getp.domain.project.commission.exception;

import es.princip.getp.domain.support.DomainLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class ApplicationDurationNotBeforeEstimatedDurationException extends DomainLogicException {

    private static final String code = "APPLICATION_DURATION_NOT_BEFORE_ESTIMATED_DURATION";
    private static final String message = "지원자 모집 기간은 예상 작업 기간보다 이전이어야 합니다.";

    public ApplicationDurationNotBeforeEstimatedDurationException() {
        super(ErrorDescription.of(code, message));
    }
}
