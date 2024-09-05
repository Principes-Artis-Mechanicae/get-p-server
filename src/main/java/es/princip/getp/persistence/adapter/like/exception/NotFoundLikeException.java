package es.princip.getp.persistence.adapter.like.exception;

import es.princip.getp.domain.support.ErrorDescription;
import es.princip.getp.persistence.support.NotFoundException;

public class NotFoundLikeException extends NotFoundException {

    private static final String code = "NOT_FOUND_LIKE";
    private static final String message = "존재하지 않는 좋아요 기록입니다.";

    public NotFoundLikeException() {
        super(ErrorDescription.of(code, message));
    }
}
