package es.princip.getp.domain.auth.exception;

import org.springframework.http.HttpStatus;
import es.princip.getp.global.exception.BusinessLogicException;

public class AlreadyVerificationCodeSendedException extends BusinessLogicException {
    private static final HttpStatus status = HttpStatus.FORBIDDEN;
    private static final String code = "ALREADY_VERIFICATION_CODE_SENDED";
    private static final String message = "사용자가 이메일 인증 유효 시간 내에 재요청";

    public AlreadyVerificationCodeSendedException() {
        super(status, code, message);
    }
}
