package es.princip.getp.application.storage.exception;

import es.princip.getp.common.exception.BusinessLogicException;
import es.princip.getp.common.exception.ErrorDescription;

public class NotSupportedExtensionException extends BusinessLogicException {

    private static final String code = "NOT_SUPPORTED_EXTENSION";
    private static final String message = "지원하지 않는 확장자입니다.";

    public NotSupportedExtensionException() {
        super(ErrorDescription.of(code, message));
    }
}