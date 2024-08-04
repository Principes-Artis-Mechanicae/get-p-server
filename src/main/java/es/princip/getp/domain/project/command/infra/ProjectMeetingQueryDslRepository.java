package es.princip.getp.domain.project.command.infra;

public interface ProjectMeetingQueryDslRepository {

    boolean existsByProjectIdAndMemberId(Long projectId, Long memberId);
}
