package es.princip.getp.application.auth.exception;

import es.princip.getp.application.support.ExternalServerException;
import es.princip.getp.domain.support.ErrorDescription;

public class FailedVerificationCodeSendingException extends ExternalServerException {

    private static final String code = "FAILED_VERIFICATION_CODE_SENDING";
    private static final String message = "인증 코드 전송에 실패했습니다. 잠시 후 다시 시도해주세요.";

    public FailedVerificationCodeSendingException() {
        super(ErrorDescription.of(code, message));
    }
}
