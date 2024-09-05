package es.princip.getp.api.security.exception;

import es.princip.getp.domain.support.ErrorDescription;

public class ExpiredTokenException extends JwtTokenException {

    private static final String code = "EXPIRED_TOKEN";

    public ExpiredTokenException(final String tokenType) {
        super(ErrorDescription.of(code ,String.format("만료된 %s Token 입니다.", tokenType)));
    }
}