package es.princip.getp.infra.storage.exception;

import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.exception.ErrorDescription;

public class NotSupportedExtensionException extends BusinessLogicException {

    private static final String code = "NOT_SUPPORTED_EXTENSION";
    private static final String message = "지원하지 않는 확장자입니다.";

    public NotSupportedExtensionException() {
        super(ErrorDescription.of(code, message));
    }
}