package es.princip.getp.domain.project.exception;

import es.princip.getp.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ProjectErrorCode implements ErrorCode {
    NOT_REGISTERED_CLIENT(HttpStatus.CONFLICT, "의뢰자 정보 미등록"),
    INVALID_ESTIMATED_DURATION(HttpStatus.CONFLICT, "유효하지 않은 예상 작업 기간"),
    INVALID_APPLICATION_DEADLINE(HttpStatus.CONFLICT, "유효하지 않은 지원자 모집 마감일");

    private final HttpStatus status;
    private final String message;

    ProjectErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public String message() {
        return this.message;
    }

    public String code() {
        return this.name();
    }

    public HttpStatus status() {
        return this.status;
    }
}
