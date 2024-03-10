package es.princip.getp.domain.project.repository;

import es.princip.getp.domain.project.domain.entity.ProjectApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectApplicationRepository extends JpaRepository<ProjectApplication, Long> {

}
