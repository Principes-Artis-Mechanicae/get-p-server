package es.princip.getp.application.support;

import es.princip.getp.domain.support.ErrorDescription;
import es.princip.getp.domain.support.ErrorDescriptionException;
import lombok.Getter;

@Getter
public abstract class ForbiddenException extends ErrorDescriptionException {

    public ForbiddenException(final ErrorDescription description) {
        super(description);
    }
}