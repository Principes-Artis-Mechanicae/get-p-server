package es.princip.getp.domain.like.exception;

import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;

public class NeverLikedException extends BusinessLogicException {

    private static final String code = "NEVER_LIKED";
    private static final String message = "좋아요를 누른 적이 없습니다.";

    public NeverLikedException() {
        super(ErrorDescription.of(code, message));
    }
}
