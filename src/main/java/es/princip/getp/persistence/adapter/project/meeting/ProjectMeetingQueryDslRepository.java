package es.princip.getp.persistence.adapter.project.meeting;

interface ProjectMeetingQueryDslRepository {

    boolean existsApplicantByProjectIdAndMemberId(Long projectId, Long memberId);
}
