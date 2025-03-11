package es.princip.getp.domain.project.apply.exception;

import es.princip.getp.domain.support.DomainLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class ClosedProjectApplicationException extends DomainLogicException {

    private static final String code = "CLOSED_PROJECT_APPLICATION";
    private static final String message = "해당 프로젝트의 지원자 모집이 닫혔습니다.";

    public ClosedProjectApplicationException() {
        super(ErrorDescription.of(code, message));
    }
}
