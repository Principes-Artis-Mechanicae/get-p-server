package es.princip.getp.application.project.meeting.port.out;

import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;

public interface CheckProjectMeetingPort {

    boolean existsApplicantBy(MemberId memberId, ProjectId projectId);
}
