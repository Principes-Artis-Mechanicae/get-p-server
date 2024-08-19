package es.princip.getp.domain.auth.exception;

import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;

public class NotVerifiedEmailException extends BusinessLogicException {

    private static final String code = "NOT_VERIFIED_EMAIL";
    private static final String message = "인증되지 않은 이메일입니다. 이메일 인증을 완료해주세요.";

    public NotVerifiedEmailException() {
        super(ErrorDescription.of(code, message));
    }
}
