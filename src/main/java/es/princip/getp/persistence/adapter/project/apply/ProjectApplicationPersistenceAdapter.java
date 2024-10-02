package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.application.project.apply.port.out.CheckProjectApplicationPort;
import es.princip.getp.application.project.apply.port.out.SaveProjectApplicationPort;
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
}
