package es.princip.getp.infra.security.exception;

import es.princip.getp.common.exception.BusinessLogicException;
import es.princip.getp.common.exception.ErrorDescription;

public abstract class JwtTokenException extends BusinessLogicException {

    protected JwtTokenException(final ErrorDescription description) {
        super(description);
    }
}