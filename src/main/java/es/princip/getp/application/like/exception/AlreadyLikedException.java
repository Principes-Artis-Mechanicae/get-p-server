package es.princip.getp.application.like.exception;

import es.princip.getp.application.support.ApplicationLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class AlreadyLikedException extends ApplicationLogicException {

    private static final String code = "ALREADY_LIKED";
    private static final String message = "이미 좋아요를 눌렀습니다.";

    public AlreadyLikedException() {
        super(ErrorDescription.of(code, message));
    }
}
