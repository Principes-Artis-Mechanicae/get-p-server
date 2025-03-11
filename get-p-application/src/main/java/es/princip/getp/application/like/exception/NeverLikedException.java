package es.princip.getp.application.like.exception;

import es.princip.getp.application.support.ApplicationLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class NeverLikedException extends ApplicationLogicException {

    private static final String code = "NEVER_LIKED";
    private static final String message = "좋아요를 누른 적이 없습니다.";

    public NeverLikedException() {
        super(ErrorDescription.of(code, message));
    }
}
