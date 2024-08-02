package es.princip.getp.domain.project.exception;

import es.princip.getp.infra.exception.BusinessLogicException;

public class ProjectAlreadyLikedException extends BusinessLogicException {

    public ProjectAlreadyLikedException() {
        super("이미 좋아요를 누른 프로젝트입니다.");
    }
}
