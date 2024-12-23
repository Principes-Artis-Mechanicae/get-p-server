package es.princip.getp.persistence.adapter.project.confirmation;

import es.princip.getp.domain.project.confirmation.model.ProjectConfirmation;
import es.princip.getp.persistence.adapter.project.confirmation.model.ProjectConfirmationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

interface ProjectConfirmationJpaRepository extends JpaRepository<ProjectConfirmationJpaEntity, Long> {
}
