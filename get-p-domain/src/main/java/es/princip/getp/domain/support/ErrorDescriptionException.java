package es.princip.getp.domain.support;

import lombok.Getter;

@Getter
public abstract class ErrorDescriptionException extends RuntimeException {

    private final ErrorDescription description;
    
    public ErrorDescriptionException(final ErrorDescription description) {
        super(description.message());
        this.description = description;
    }
}