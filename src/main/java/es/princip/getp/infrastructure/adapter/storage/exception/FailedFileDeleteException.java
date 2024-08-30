package es.princip.getp.infrastructure.adapter.storage.exception;

import es.princip.getp.common.exception.ErrorDescription;
import es.princip.getp.common.exception.ExternalApiErrorException;

public class FailedFileDeleteException extends ExternalApiErrorException {

    private static final String code = "FAILED_FILE_DELETE";
    private static final String message = "파일 삭제에 실패했습니다. 잠시 후 다시 시도해주세요.";

    public FailedFileDeleteException() {
        super(ErrorDescription.of(code, message));
    }
}