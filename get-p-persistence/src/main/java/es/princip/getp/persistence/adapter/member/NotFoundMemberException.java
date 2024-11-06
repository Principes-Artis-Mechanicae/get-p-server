package es.princip.getp.persistence.adapter.member;

import es.princip.getp.domain.support.ErrorDescription;
import es.princip.getp.persistence.support.NotFoundException;

public class NotFoundMemberException extends NotFoundException {

    private static final String code = "NOT_FOUND_MEMBER";
    private static final String message = "존재하지 않는 회원입니다.";

    public NotFoundMemberException() {
        super(ErrorDescription.of(code, message));
    }
}
