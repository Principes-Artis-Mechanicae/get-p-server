package es.princip.getp.domain.project.query.dao;

import es.princip.getp.domain.project.commission.model.Project;

import java.util.List;

public class ProjectDaoUtil {

    public static Long[] toProjectIds(final List<Project> projects) {
        return projects.stream()
            .map(Project::getProjectId)
            .toArray(Long[]::new);
    }
}
