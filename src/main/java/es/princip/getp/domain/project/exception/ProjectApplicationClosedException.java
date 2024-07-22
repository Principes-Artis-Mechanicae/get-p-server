package es.princip.getp.domain.project.exception;

import es.princip.getp.infra.exception.BusinessLogicException;

public class ProjectApplicationClosedException extends BusinessLogicException {

    public ProjectApplicationClosedException() {
        super("해당 프로젝트의 지원자 모집이 닫혔습니다.");
    }
}
