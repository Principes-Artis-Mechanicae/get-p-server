package es.princip.getp.domain.project.exception;

import es.princip.getp.infra.exception.ErrorCode;
import es.princip.getp.infra.exception.ErrorDescription;
import org.springframework.http.HttpStatus;

public enum ProjectLikeErrorCode implements ErrorCode {
    ALREADY_LIKED(HttpStatus.CONFLICT, "이미 좋아요를 누른 프로젝트입니다.");

    private final HttpStatus status;
    private final ErrorDescription description;

    ProjectLikeErrorCode(HttpStatus status, String message) {
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
