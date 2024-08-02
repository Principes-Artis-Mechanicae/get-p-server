package es.princip.getp.domain.member.command.exception;

import es.princip.getp.infra.exception.ErrorDescription;
import es.princip.getp.infra.exception.NotFoundException;

public class NotFoundMemberException extends NotFoundException {

    private static final String code = "NOT_FOUND_MEMBER";
    private static final String message = "존재하지 않는 회원입니다.";

    public NotFoundMemberException() {
        super(ErrorDescription.of(code, message));
    }
}
