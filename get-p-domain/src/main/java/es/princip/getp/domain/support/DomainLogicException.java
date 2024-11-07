package es.princip.getp.domain.support;

import lombok.Getter;

@Getter
public abstract class DomainLogicException extends ErrorDescriptionException {

    public DomainLogicException(final ErrorDescription description) {
        super(description);
    }
}