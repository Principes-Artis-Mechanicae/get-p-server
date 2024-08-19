package es.princip.getp.domain.auth.exception;

import es.princip.getp.common.exception.BusinessLogicException;
import es.princip.getp.common.exception.ErrorDescription;

public class IncorrectEmailOrPasswordException extends BusinessLogicException {

    private static final String code = "INCORRECT_EMAIL_OR_PASSWORD";
    private static final String message = "이메일 또는 비밀번호가 일치하지 않습니다.";

    public IncorrectEmailOrPasswordException() {
        super(ErrorDescription.of(code, message));
    }
}
