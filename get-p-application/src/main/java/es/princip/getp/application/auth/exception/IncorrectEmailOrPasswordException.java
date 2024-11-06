package es.princip.getp.application.auth.exception;

import es.princip.getp.application.support.ApplicationLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class IncorrectEmailOrPasswordException extends ApplicationLogicException {

    private static final String code = "INCORRECT_EMAIL_OR_PASSWORD";
    private static final String message = "이메일 또는 비밀번호가 일치하지 않습니다.";

    public IncorrectEmailOrPasswordException() {
        super(ErrorDescription.of(code, message));
    }
}
