package es.princip.getp.domain.auth.exception;

import es.princip.getp.common.exception.BusinessLogicException;
import es.princip.getp.common.exception.ErrorDescription;

public class IncorrectVerificationCodeException extends BusinessLogicException {

    private static final String code = "INCORRECT_VERIFICATION_CODE";
    private static final String message = "인증 코드가 일치하지 않습니다.";

    public IncorrectVerificationCodeException() {
        super(ErrorDescription.of(code, message));
    }
}
