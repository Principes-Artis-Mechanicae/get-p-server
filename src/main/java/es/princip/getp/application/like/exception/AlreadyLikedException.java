package es.princip.getp.application.like.exception;

import es.princip.getp.common.exception.BusinessLogicException;
import es.princip.getp.common.exception.ErrorDescription;

public class AlreadyLikedException extends BusinessLogicException {

    private static final String code = "ALREADY_LIKED";
    private static final String message = "이미 좋아요를 눌렀습니다.";

    public AlreadyLikedException() {
        super(ErrorDescription.of(code, message));
    }
}
