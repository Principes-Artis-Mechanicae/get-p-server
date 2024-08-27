package es.princip.getp.application.project.meeting.port.out;

public interface CheckProjectMeetingPort {

    boolean existsApplicantByProjectIdAndMemberId(Long memberId, Long projectId);
}
