package es.princip.getp.persistence.adapter.project.confirmation;

import es.princip.getp.application.project.confirmation.port.out.SaveProjectConfirmationPort;
import es.princip.getp.domain.project.confirmation.model.ProjectConfirmation;
import es.princip.getp.persistence.adapter.project.confirmation.model.ProjectConfirmationJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ProjectConfirmationPersistenceAdapter implements
    SaveProjectConfirmationPort {

    private final ProjectConfirmationJpaRepository jpaRepository;
    private final ProjectConfirmationPersistenceMapper mapper;

    @Override
    public Long save(final ProjectConfirmation projectConfirmation) {
        final ProjectConfirmationJpaEntity jpaEntity = mapper.mapToJpa(projectConfirmation);
        return jpaRepository.save(jpaEntity).getId();
    }
}
