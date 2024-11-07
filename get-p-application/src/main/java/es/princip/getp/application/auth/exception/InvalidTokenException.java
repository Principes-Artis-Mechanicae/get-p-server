package es.princip.getp.application.auth.exception;

import es.princip.getp.domain.support.ErrorDescription;

public class InvalidTokenException extends JwtTokenException {

    private static final String code = "INVALID_TOKEN";

    public InvalidTokenException(final String tokenType) {
        super(ErrorDescription.of(code, String.format("유효하지 않은 %s Token 입니다.", tokenType)));
    }
}