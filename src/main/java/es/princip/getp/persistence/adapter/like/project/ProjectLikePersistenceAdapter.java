package es.princip.getp.persistence.adapter.like.project;

import es.princip.getp.application.like.project.port.out.CheckProjectLikePort;
import es.princip.getp.application.like.project.port.out.DeleteProjectLikePort;
import es.princip.getp.application.like.project.port.out.LoadProjectLikePort;
import es.princip.getp.application.like.project.port.out.SaveProjectLikePort;
import es.princip.getp.domain.like.project.model.ProjectLike;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.persistence.adapter.like.exception.NotFoundLikeException;
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
    public boolean existsBy(final PeopleId peopleId, Long projectId) {
        return repository.existsByPeopleIdAndProjectId(peopleId.getValue(), projectId);
    }

    @Override
    public void save(final ProjectLike like) {
        final ProjectLikeJpaEntity jpaEntity = mapper.mapToJpa(like);
        repository.save(jpaEntity);
    }

    @Override
    public ProjectLike loadBy(final PeopleId peopleId, final Long projectId) {
        final ProjectLikeJpaEntity jpaEntity = repository.findByPeopleIdAndProjectId(peopleId.getValue(), projectId)
            .orElseThrow(NotFoundLikeException::new);
        return mapper.mapToDomain(jpaEntity);
    }
}