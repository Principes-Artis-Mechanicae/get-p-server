package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.application.project.apply.port.out.CheckProjectApplicationPort;
import es.princip.getp.application.project.apply.port.out.SaveProjectApplicationPort;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
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
    public Long save(final ProjectApplication application) {
        final ProjectApplicationJpaEntity jpaEntity = mapper.mapToJpa(application);
        return repository.save(jpaEntity).getApplicationId();
    }

    @Override
    public boolean existsByApplicantIdAndProjectId(final Long applicantId, final Long projectId) {
        return repository.existsByApplicantIdAndProjectId(applicantId, projectId);
    }
}
