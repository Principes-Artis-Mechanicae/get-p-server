package es.princip.getp.application.storage.exception;

import es.princip.getp.domain.support.ErrorDescription;

public class FailedFileDeleteException extends FileStorageException {

    private static final String code = "FAILED_FILE_DELETE";
    private static final String message = "파일 삭제에 실패했습니다. 잠시 후 다시 시도해주세요.";

    public FailedFileDeleteException() {
        super(ErrorDescription.of(code, message));
    }
}