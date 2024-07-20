package es.princip.getp.domain.member.command.exception;

import es.princip.getp.infra.exception.BusinessLogicException;

public class FailedToSaveProfileImageException extends BusinessLogicException {

    public FailedToSaveProfileImageException() {
        super("프로필 이미지를 저장하는데 실패했습니다.");
    }
}
