package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.application.project.apply.port.out.CheckProjectApplicationPort;
import es.princip.getp.application.project.apply.port.out.LoadProjectApplicantPort;
import es.princip.getp.application.project.apply.port.out.SaveProjectApplicationPort;
import es.princip.getp.application.project.apply.port.out.UpdateProjectApplicantPort;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.persistence.adapter.project.apply.model.ProjectApplicationJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ProjectApplicationPersistenceAdapter implements
    SaveProjectApplicationPort,
    LoadProjectApplicantPort,
    UpdateProjectApplicantPort,
    CheckProjectApplicationPort {

    private final ProjectApplicationPersistenceMapper mapper;
    private final ProjectApplicationJpaRepository repository;

    @Override
    public ProjectApplicationId save(final ProjectApplication application) {
        final ProjectApplicationJpaEntity jpaEntity = mapper.mapToJpa(application);
        return new ProjectApplicationId(repository.save(jpaEntity).getId());
    }

    @Override
    public boolean existsBy(final PeopleId applicantId, final ProjectId projectId) {
        return repository.existsByApplicantIdAndProjectId(applicantId.getValue(), projectId.getValue());
    }

    @Override
    public ProjectApplication loadBy(final ProjectApplicationId applicationId) {
        final ProjectApplicationJpaEntity jpaEntity = repository.findById(applicationId.getValue())
            .orElseThrow(NotFoundProjectApplicationException::new);
        return mapper.mapToDomain(jpaEntity);
    }

    @Override
    public void update(final ProjectApplication application) {
        final Long applicationId = application.getId().getValue();
        if (!repository.existsById(applicationId)) {
            throw new NotFoundProjectApplicationException();
        }
        final ProjectApplicationJpaEntity jpaEntity = mapper.mapToJpa(application);
        repository.save(jpaEntity);
    }
}
