package es.princip.getp.domain.member.command.exception;

import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;

public class AlreadyUsedEmailException extends BusinessLogicException {

    private static final String code = "ALREADY_USED_EMAIL";
    private static final String message = "이미 사용 중인 이메일입니다.";

    public AlreadyUsedEmailException() {
        super(ErrorDescription.of(code, message));
    }
}
