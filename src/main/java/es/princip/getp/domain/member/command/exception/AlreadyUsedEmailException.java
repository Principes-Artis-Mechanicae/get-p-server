package es.princip.getp.domain.member.command.exception;

import es.princip.getp.infra.exception.BusinessLogicException;

public class AlreadyUsedEmailException extends BusinessLogicException {

    public AlreadyUsedEmailException() {
        super("이미 사용 중인 이메일입니다.");
    }
}
