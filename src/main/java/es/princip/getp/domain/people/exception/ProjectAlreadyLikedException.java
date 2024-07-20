package es.princip.getp.domain.people.exception;

import es.princip.getp.infra.exception.BusinessLogicException;

public class ProjectAlreadyLikedException extends BusinessLogicException {

    public ProjectAlreadyLikedException() {
        super("이미 좋아요한 프로젝트입니다.");
    }
}
