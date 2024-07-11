package es.princip.getp.domain.project.domain;

import es.princip.getp.domain.project.infra.ProjectQueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectQueryDslRepository {

}