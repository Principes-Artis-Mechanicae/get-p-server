package es.princip.getp.persistence.adapter.project;

import es.princip.getp.domain.project.commission.model.Project;

import java.util.List;

public class ProjectPersistenceUtil {

    public static Long[] toProjectIds(final List<Project> projects) {
        return projects.stream()
            .map(Project::getProjectId)
            .toArray(Long[]::new);
    }
}
