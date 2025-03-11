package es.princip.getp.persistence.support;

import es.princip.getp.domain.support.ErrorDescription;
import es.princip.getp.domain.support.ErrorDescriptionException;
import lombok.Getter;

@Getter
public abstract class NotFoundException extends ErrorDescriptionException {

    public NotFoundException(final ErrorDescription description) {
        super(description);
    }
}