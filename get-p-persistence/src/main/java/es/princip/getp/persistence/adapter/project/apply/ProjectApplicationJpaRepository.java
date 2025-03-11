package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.persistence.adapter.project.apply.model.ProjectApplicationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ProjectApplicationJpaRepository extends JpaRepository<ProjectApplicationJpaEntity, Long> {

    boolean existsByApplicantIdAndProjectId(Long applicantId, Long projectId);

    Optional<ProjectApplicationJpaEntity> findByProjectIdAndApplicantId(Long projectId, Long applicantId);
}