package es.princip.getp.domain.project.command.domain;

public interface ProjectMeetingQueryDslRepository {
    boolean existsByProjectIdAndMemberId(Long projectId, Long memberId);
}
