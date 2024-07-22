package es.princip.getp.domain.people.exception;

import es.princip.getp.infra.exception.BusinessLogicException;

public class ProjectNeverLikedException extends BusinessLogicException {

    public ProjectNeverLikedException() {
        super("좋아요를 누르지 않은 프로젝트입니다.");
    }
}
