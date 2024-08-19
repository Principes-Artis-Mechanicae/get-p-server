package es.princip.getp.storage.exception;

import es.princip.getp.common.exception.ErrorDescription;
import es.princip.getp.common.exception.ExternalApiErrorException;

public class FailedFileSaveException extends ExternalApiErrorException {

    private static final String code = "FAILED_FILE_SAVE";
    private static final String message = "파일 저장에 실패했습니다. 잠시 후 다시 시도해주세요.";

    public FailedFileSaveException() {
        super(ErrorDescription.of(code, message));
    }
}