package es.princip.getp.domain.project.infra;

import es.princip.getp.domain.project.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectQueryDslRepository {
    Page<Project> findProjectPage(Pageable pageable);
}