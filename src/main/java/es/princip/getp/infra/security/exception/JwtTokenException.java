package es.princip.getp.infra.security.exception;

import es.princip.getp.infra.exception.BusinessLogicException;

public abstract class JwtTokenException extends BusinessLogicException {

    protected JwtTokenException(final String message) {
        super(message);
    }
}