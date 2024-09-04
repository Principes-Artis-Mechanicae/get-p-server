package es.princip.getp.persistence.adapter.like.command.project;

import es.princip.getp.application.like.port.out.project.CheckProjectLikePort;
import es.princip.getp.application.like.port.out.project.LoadProjectLikePort;
import es.princip.getp.application.like.port.out.project.ProjectLikePort;
import es.princip.getp.application.like.port.out.project.ProjectUnlikePort;
import es.princip.getp.domain.like.model.project.ProjectLike;
import es.princip.getp.persistence.adapter.like.command.ProjectLikePersistenceMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ProjectLikePersistenceAdapter implements
        ProjectLikePort,
        CheckProjectLikePort,
        ProjectUnlikePort,
        LoadProjectLikePort {

    private final ProjectLikeJpaRepository repository;
    private final ProjectLikePersistenceMapper mapper;
    
    @Override
    public void unlike(ProjectLike projectLike) {
        ProjectLikeJpaEntity jpaEntity = mapper.mapToJpa(projectLike);
        repository.deleteById(jpaEntity.getId());
    }

    @Override
    public boolean existsByPeopleIdAndProjectId(Long peopleId, Long projectId) {
        return repository.existsByPeopleIdAndProjectId(peopleId, projectId);
    }

    @Override
    public void like(ProjectLike like) {
        final ProjectLikeJpaEntity jpaEntity = mapper.mapToJpa(like);
        repository.save(jpaEntity);
    }

    @Override
    public ProjectLike findByPeopleIdAndProjectId(Long peopleId, Long projectId) {
        ProjectLikeJpaEntity jpaEntity = repository.findByPeopleIdAndProjectId(peopleId, projectId);
        return mapper.mapToDomain(jpaEntity);
    }
}