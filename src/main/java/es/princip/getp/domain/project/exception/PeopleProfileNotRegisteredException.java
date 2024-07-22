package es.princip.getp.domain.project.exception;

import es.princip.getp.infra.exception.BusinessLogicException;

public class PeopleProfileNotRegisteredException extends BusinessLogicException {

    public PeopleProfileNotRegisteredException() {
        super("프로젝트에 지원하려면 피플 프로필을 등록해야 합니다.");
    }
}
