package es.princip.getp.persistence.adapter.project;

import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;

import java.util.List;

public class ProjectPersistenceUtil {

    public static ProjectId[] toProjectIds(final List<Project> projects) {
        return projects.stream()
            .map(Project::getId)
            .toArray(ProjectId[]::new);
    }
}
