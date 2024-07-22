package es.princip.getp.domain.project.exception;

import es.princip.getp.infra.exception.BusinessLogicException;

public class ApplicationDurationIsNotBeforeEstimatedDurationException extends BusinessLogicException {

    public ApplicationDurationIsNotBeforeEstimatedDurationException() {
        super("지원자 모집 기간은 예상 작업 기간보다 이전이어야 합니다.");
    }
}
