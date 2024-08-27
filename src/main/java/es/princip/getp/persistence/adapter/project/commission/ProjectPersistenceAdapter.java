package es.princip.getp.persistence.adapter.project.commission;

import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.application.project.commission.port.out.SaveProjectPort;
import es.princip.getp.application.project.commission.port.out.UpdateProjectPort;
import es.princip.getp.domain.project.commission.model.Project;
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
    public Long save(final Project project) {
        final ProjectJpaEntity jpaEntity = mapper.mapToJpa(project);
        return repository.save(jpaEntity).getProjectId();
    }

    @Override
    public Project loadBy(final Long meetingId) {
        final ProjectJpaEntity jpaEntity = repository.findById(meetingId)
            .orElseThrow(NotFoundProjectException::new);
        return mapper.mapToDomain(jpaEntity);
    }

    @Override
    public void update(final Project project) {
        final Long projectId = project.getProjectId();
        if (!repository.existsById(projectId)) {
            throw new NotFoundProjectException();
        }
        final ProjectJpaEntity jpaEntity = mapper.mapToJpa(project);
        repository.save(jpaEntity);
    }
}
