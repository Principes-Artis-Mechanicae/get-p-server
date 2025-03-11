package es.princip.getp.persistence.adapter.project.assign;

import es.princip.getp.persistence.adapter.project.assign.model.AssignmentPeopleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

interface AssignmentPeopleJpaRepository extends JpaRepository<AssignmentPeopleJpaEntity, Long> {
}
