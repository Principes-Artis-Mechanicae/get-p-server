package es.princip.getp.application.auth.exception;

import es.princip.getp.domain.support.ErrorDescription;
import es.princip.getp.persistence.support.NotFoundException;

public class NotFoundVerificationException extends NotFoundException {

    private static final String code = "NOT_FOUND_VERIFICATION";
    private static final String message = "존재하지 않는 인증입니다. 인증 요청 후 다시 시도해주세요.";

    public NotFoundVerificationException() {
        super(ErrorDescription.of(code, message));
    }
}
