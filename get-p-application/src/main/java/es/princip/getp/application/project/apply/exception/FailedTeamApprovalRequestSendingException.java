package es.princip.getp.application.project.apply.exception;

import es.princip.getp.application.support.ExternalServerException;
import es.princip.getp.domain.support.ErrorDescription;

public class FailedTeamApprovalRequestSendingException extends ExternalServerException {

    private static final String code = "FAILED_TEAM_APPROVAL_REQUEST_SENDING";
    private static final String message = "팀원 승인 신청 전송에 실패했습니다. 잠시 후 다시 시도해주세요.";

    public FailedTeamApprovalRequestSendingException() {
        super(ErrorDescription.of(code, message));
    }
}
