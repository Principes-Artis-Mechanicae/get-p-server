package es.princip.getp.persistence.adapter.project.commission;

import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.application.project.commission.port.out.SaveProjectPort;
import es.princip.getp.application.project.commission.port.out.UpdateProjectPort;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.persistence.adapter.project.ProjectPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ProjectPersistenceAdapter implements
        SaveProjectPort,
        LoadProjectPort,
        UpdateProjectPort {

    private final ProjectPersistenceMapper mapper;
    private final ProjectJpaRepository repository;

    @Override
    public ProjectId save(final Project project) {
        final ProjectJpaEntity jpaEntity = mapper.mapToJpa(project);
        return new ProjectId(repository.save(jpaEntity).getId());
    }

    @Override
    public Project loadBy(final ProjectId projectId) {
        final ProjectJpaEntity jpaEntity = repository.findById(projectId.getValue())
            .orElseThrow(NotFoundProjectException::new);
        return mapper.mapToDomain(jpaEntity);
    }

    @Override
    public void update(final Project project) {
        final Long projectId = project.getId().getValue();
        if (!repository.existsById(projectId)) {
            throw new NotFoundProjectException();
        }
        final ProjectJpaEntity jpaEntity = mapper.mapToJpa(project);
        repository.save(jpaEntity);
    }
}
