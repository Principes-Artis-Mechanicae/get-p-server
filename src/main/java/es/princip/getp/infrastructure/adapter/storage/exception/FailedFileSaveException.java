package es.princip.getp.infrastructure.adapter.storage.exception;

import es.princip.getp.domain.support.ErrorDescription;
import es.princip.getp.infrastructure.support.FileStorageException;

public class FailedFileSaveException extends FileStorageException {

    private static final String code = "FAILED_FILE_SAVE";
    private static final String message = "파일 저장에 실패했습니다. 잠시 후 다시 시도해주세요.";

    public FailedFileSaveException() {
        super(ErrorDescription.of(code, message));
    }
}