package es.princip.getp.application.projectMeeting.port.out;

public interface CheckProjectMeetingPort {
    boolean existsByProjectIdAndMemberId(Long memberId, Long projectId);
}
