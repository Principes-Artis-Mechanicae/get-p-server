package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.persistence.adapter.project.apply.model.ProjectApplicationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ProjectApplicationJpaRepository extends JpaRepository<ProjectApplicationJpaEntity, Long> {

    boolean existsByApplicantIdAndProjectId(Long applicantId, Long projectId);
}