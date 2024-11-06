package es.princip.getp.persistence.adapter.like.project;

import es.princip.getp.application.like.project.port.out.CheckProjectLikePort;
import es.princip.getp.application.like.project.port.out.DeleteProjectLikePort;
import es.princip.getp.application.like.project.port.out.LoadProjectLikePort;
import es.princip.getp.application.like.project.port.out.SaveProjectLikePort;
import es.princip.getp.domain.like.project.model.ProjectLike;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.application.like.exception.NotFoundLikeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ProjectLikePersistenceAdapter implements
    SaveProjectLikePort,
    CheckProjectLikePort,
    DeleteProjectLikePort,
    LoadProjectLikePort {

    private final ProjectLikeJpaRepository repository;
    private final ProjectLikePersistenceMapper mapper;
    
    @Override
    public void delete(final ProjectLike projectLike) {
        final ProjectLikeJpaEntity jpaEntity = mapper.mapToJpa(projectLike);
        repository.deleteById(jpaEntity.getId());
    }

    @Override
    public Boolean existsBy(final Member member, final ProjectId projectId) {
        if (member == null || member.isClient()) {
            return null;
        }
        return existsBy(member.getId(), projectId);
    }

    @Override
    public void save(final ProjectLike like) {
        final ProjectLikeJpaEntity jpaEntity = mapper.mapToJpa(like);
        repository.save(jpaEntity);
    }

    @Override
    public ProjectLike loadBy(final MemberId memberId, final ProjectId projectId) {
        final ProjectLikeJpaEntity jpaEntity = repository.findByMemberIdAndProjectId(
                memberId.getValue(),
                projectId.getValue()
            )
            .orElseThrow(NotFoundLikeException::new);
        return mapper.mapToDomain(jpaEntity);
    }

    @Override
    public boolean existsBy(final MemberId memberId, final ProjectId projectId) {
        return repository.existsByMemberIdAndProjectId(
            memberId.getValue(),
            projectId.getValue()
        );
    }
}