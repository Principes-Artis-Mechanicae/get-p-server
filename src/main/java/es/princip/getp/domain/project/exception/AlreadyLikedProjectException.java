package es.princip.getp.domain.project.exception;

import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public class AlreadyLikedProjectException extends BusinessLogicException {

    private static final HttpStatus status = HttpStatus.CONFLICT;
    private static final String code = "ALREADY_LIKED_PROJECT";
    private static final String message = "이미 좋아요를 누른 프로젝트입니다.";

    public AlreadyLikedProjectException() {
        super(status, ErrorDescription.of(code, message));
    }
}
