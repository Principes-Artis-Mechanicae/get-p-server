package es.princip.getp.domain.project.repository;

import es.princip.getp.domain.project.domain.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectQueryDslRepository {
    Page<Project> findProjectPage(Pageable pageable);
}