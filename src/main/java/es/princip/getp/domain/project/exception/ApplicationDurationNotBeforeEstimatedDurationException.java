package es.princip.getp.domain.project.exception;

import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public class ApplicationDurationNotBeforeEstimatedDurationException extends BusinessLogicException {

    private static final HttpStatus status = HttpStatus.CONFLICT;
    private static final String code = "APPLICATION_DURATION_NOT_BEFORE_ESTIMATED_DURATION";
    private static final String message = "지원자 모집 기간은 예상 작업 기간보다 이전이어야 합니다.";

    public ApplicationDurationNotBeforeEstimatedDurationException() {
        super(status, ErrorDescription.of(code, message));
    }
}
