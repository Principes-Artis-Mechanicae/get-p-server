package es.princip.getp.domain.project.apply;

import es.princip.getp.domain.project.apply.model.ProjectApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectApplicationRepository extends JpaRepository<ProjectApplication, Long> {

    boolean existsByApplicantIdAndProjectId(Long applicantId, Long projectId);
}