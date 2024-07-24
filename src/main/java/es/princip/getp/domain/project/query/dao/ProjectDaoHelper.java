package es.princip.getp.domain.project.query.dao;

import es.princip.getp.domain.project.command.domain.Project;

import java.util.List;

public class ProjectDaoHelper {

    public static Long[] toProjectIds(final List<Project> projects) {
        return projects.stream()
            .map(Project::getProjectId)
            .toArray(Long[]::new);
    }
}
