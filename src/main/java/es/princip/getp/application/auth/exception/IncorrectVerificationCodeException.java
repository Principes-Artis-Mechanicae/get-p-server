package es.princip.getp.application.auth.exception;

import es.princip.getp.application.support.ApplicationLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class IncorrectVerificationCodeException extends ApplicationLogicException {

    private static final String code = "INCORRECT_VERIFICATION_CODE";
    private static final String message = "인증 코드가 일치하지 않습니다.";

    public IncorrectVerificationCodeException() {
        super(ErrorDescription.of(code, message));
    }
}
