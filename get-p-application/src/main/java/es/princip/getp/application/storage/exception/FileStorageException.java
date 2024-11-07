package es.princip.getp.application.storage.exception;

import es.princip.getp.domain.support.ErrorDescription;
import es.princip.getp.domain.support.ErrorDescriptionException;
import lombok.Getter;

@Getter
public abstract class FileStorageException extends ErrorDescriptionException {

    public FileStorageException(final ErrorDescription description) {
        super(description);
    }
}