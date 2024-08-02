package es.princip.getp.domain.project.exception;

import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public class NeverLikedProjectException extends BusinessLogicException {

    private static final HttpStatus status = HttpStatus.CONFLICT;
    private static final String code = "NEVER_LIKED_PROJECT";
    private static final String message = "좋아요를 누르지 않은 프로젝트입니다.";

    public NeverLikedProjectException() {
        super(status, ErrorDescription.of(code, message));
    }
}
