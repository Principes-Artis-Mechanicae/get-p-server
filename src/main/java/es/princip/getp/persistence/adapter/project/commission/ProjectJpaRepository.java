package es.princip.getp.persistence.adapter.project.commission;

import org.springframework.data.jpa.repository.JpaRepository;

interface ProjectJpaRepository extends JpaRepository<ProjectJpaEntity, Long> {
}