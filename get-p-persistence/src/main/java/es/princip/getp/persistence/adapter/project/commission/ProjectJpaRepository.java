package es.princip.getp.persistence.adapter.project.commission;

import es.princip.getp.persistence.adapter.project.commission.model.ProjectJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

interface ProjectJpaRepository extends JpaRepository<ProjectJpaEntity, Long> {
}