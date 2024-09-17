package es.princip.getp.application.project.meeting.port.out;

import es.princip.getp.domain.member.model.MemberId;

public interface CheckProjectMeetingPort {

    boolean existsApplicantBy(MemberId memberId, Long projectId);
}
