package es.princip.getp.infra.security.exception;

public class InvalidTokenException extends JwtTokenException {

    public InvalidTokenException(final String tokenType) {
        super(String.format("유효하지 않은 %s Token 입니다.", tokenType));
    }
}