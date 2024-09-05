package es.princip.getp.api.security.exception;

import es.princip.getp.domain.support.DomainLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public abstract class JwtTokenException extends DomainLogicException {

    protected JwtTokenException(final ErrorDescription description) {
        super(description);
    }
}