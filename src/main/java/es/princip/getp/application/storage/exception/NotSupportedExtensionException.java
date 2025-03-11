package es.princip.getp.application.storage.exception;

import es.princip.getp.application.support.ApplicationLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class NotSupportedExtensionException extends ApplicationLogicException {

    private static final String code = "NOT_SUPPORTED_EXTENSION";
    private static final String message = "지원하지 않는 확장자입니다.";

    public NotSupportedExtensionException() {
        super(ErrorDescription.of(code, message));
    }
}