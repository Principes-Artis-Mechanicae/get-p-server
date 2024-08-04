package es.princip.getp.domain.project.exception;

import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;

public class NeverLikedProjectException extends BusinessLogicException {

    private static final String code = "NEVER_LIKED_PROJECT";
    private static final String message = "좋아요를 누르지 않은 프로젝트입니다.";

    public NeverLikedProjectException() {
        super(ErrorDescription.of(code, message));
    }
}
