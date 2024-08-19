package es.princip.getp.domain.auth.exception;

import es.princip.getp.common.exception.ErrorDescription;
import es.princip.getp.common.exception.ExternalApiErrorException;

public class FailedVerificationCodeSendingException extends ExternalApiErrorException {

    private static final String code = "FAILED_VERIFICATION_CODE_SENDING";
    private static final String message = "인증 코드 전송에 실패했습니다. 잠시 후 다시 시도해주세요.";

    public FailedVerificationCodeSendingException() {
        super(ErrorDescription.of(code, message));
    }
}
