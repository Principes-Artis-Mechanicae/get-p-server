package es.princip.getp.domain.project.exception;

import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;

public class ClosedProjectApplicationException extends BusinessLogicException {

    private static final String code = "CLOSED_PROJECT_APPLICATION";
    private static final String message = "해당 프로젝트의 지원자 모집이 닫혔습니다.";

    public ClosedProjectApplicationException() {
        super(ErrorDescription.of(code, message));
    }
}