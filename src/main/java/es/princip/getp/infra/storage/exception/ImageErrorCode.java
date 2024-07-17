package es.princip.getp.infra.storage.exception;

import es.princip.getp.infra.exception.ErrorCode;
import es.princip.getp.infra.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public enum ImageErrorCode implements ErrorCode {

    INVALID_IMAGE_FILE(HttpStatus.BAD_REQUEST, "유효하지 않은 이미지 파일 입니다."),
    NOT_ALLOWED_EXTENSION(HttpStatus.CONFLICT, "허용되지 않는 확장자 입니다."),
    IMAGE_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 저장에 실패하였습니다."),
    IMAGE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 삭제에 실패하였습니다.");

    private final HttpStatus status;
    private final ErrorDescription description;

    ImageErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.description = ErrorDescription.of(this.name(), message);
    }

    public HttpStatus status() {
        return status;
    }

    public ErrorDescription description() {
        return description;
    }
}
