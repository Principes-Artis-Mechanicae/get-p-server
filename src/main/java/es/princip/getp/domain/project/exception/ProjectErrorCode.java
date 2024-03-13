package es.princip.getp.domain.project.exception;

import es.princip.getp.global.exception.ErrorCode;
import es.princip.getp.global.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public enum ProjectErrorCode implements ErrorCode {
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "프로젝트를 찾을 수 없음"),
    NOT_REGISTERED_CLIENT(HttpStatus.CONFLICT, "의뢰자 정보 미등록"),
    INVALID_ESTIMATED_DURATION(HttpStatus.CONFLICT, "유효하지 않은 예상 작업 기간"),
    INVALID_APPLICATION_DEADLINE(HttpStatus.CONFLICT, "유효하지 않은 지원자 모집 마감일");

    private final HttpStatus status;
    private final ErrorDescription description;

    ProjectErrorCode(HttpStatus status, String message) {
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
