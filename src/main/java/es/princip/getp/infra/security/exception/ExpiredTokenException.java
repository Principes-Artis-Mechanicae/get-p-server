package es.princip.getp.infra.security.exception;

public class ExpiredTokenException extends JwtTokenException {

    public ExpiredTokenException(final String tokenType) {
        super(String.format("만료된 %s Token 입니다.", tokenType));
    }
}