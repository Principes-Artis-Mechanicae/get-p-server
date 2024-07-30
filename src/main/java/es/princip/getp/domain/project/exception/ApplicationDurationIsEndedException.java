package es.princip.getp.domain.project.exception;

import es.princip.getp.infra.exception.BusinessLogicException;

public class ApplicationDurationIsEndedException extends BusinessLogicException {

    public ApplicationDurationIsEndedException() {
        super("지원자 모집 기간은 금일부터거나 이후여야 합니다.");
    }
}
