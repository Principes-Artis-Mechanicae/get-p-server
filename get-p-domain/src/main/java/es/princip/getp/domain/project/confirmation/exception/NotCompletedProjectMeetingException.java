package es.princip.getp.domain.project.confirmation.exception;

import es.princip.getp.domain.support.DomainLogicException;
import es.princip.getp.domain.support.ErrorDescription;

public class NotCompletedProjectMeetingException extends DomainLogicException {

        private static final String code = "NOT_COMPLETED_PROJECT_MEETING";
        private static final String message = "미팅이 완료되지 않은 프로젝트는 확정할 수 없습니다.";

        public NotCompletedProjectMeetingException() {
            super(ErrorDescription.of(code, message));
        }
}
