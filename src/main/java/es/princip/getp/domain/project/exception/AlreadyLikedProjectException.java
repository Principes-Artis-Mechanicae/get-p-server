package es.princip.getp.domain.project.exception;

import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;

public class AlreadyLikedProjectException extends BusinessLogicException {

    private static final String code = "ALREADY_LIKED_PROJECT";
    private static final String message = "이미 좋아요를 누른 프로젝트입니다.";

    public AlreadyLikedProjectException() {
        super(ErrorDescription.of(code, message));
    }
}
