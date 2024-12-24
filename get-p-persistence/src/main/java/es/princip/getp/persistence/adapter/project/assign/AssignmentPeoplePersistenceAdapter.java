package es.princip.getp.persistence.adapter.project.assign;

import es.princip.getp.application.project.assign.port.out.SaveAssignmentPeoplePort;
import es.princip.getp.domain.project.confirmation.model.AssignmentPeople;
import es.princip.getp.persistence.adapter.project.assign.model.AssignmentPeopleJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class AssignmentPeoplePersistenceAdapter implements
        SaveAssignmentPeoplePort {

    private final AssignmentPeopleJpaRepository jpaRepository;
    private final AssignmentPeoplePersistenceMapper mapper;

    @Override
    public Long save(final AssignmentPeople assignmentPeople) {
        final AssignmentPeopleJpaEntity jpaEntity = mapper.mapToJpa(assignmentPeople);
        return jpaRepository.save(jpaEntity).getId();
    }
}
