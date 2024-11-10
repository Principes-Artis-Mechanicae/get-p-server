package es.princip.getp.batch.project;

import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static es.princip.getp.fixture.project.ProjectFixture.project;

@Service
public class ProjectFactory {

    public List<Project> createProjects(final int size) {
        final List<Project> projects = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            final Long id = (long) i;
            projects.add(project(
                new ProjectId(id),
                new ClientId(id),
                ProjectStatus.APPLICATION_OPENED)
            );
        }
        return projects;
    }
}
