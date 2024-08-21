package es.princip.getp.domain.project.query.infra;

import es.princip.getp.common.util.DataLoader;
import es.princip.getp.domain.project.command.domain.Project;
import es.princip.getp.domain.project.command.domain.ProjectStatus;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.fixture.project.ProjectFixture.project;

@RequiredArgsConstructor
public class ProjectDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Override
    public void load(final int size) {
        final List<Project> projectList = LongStream.rangeClosed(1, size)
            .boxed()
            .flatMap(clientId -> Arrays.stream(ProjectStatus.values())
                .map(status -> project(clientId, status))
            )
            .toList();
        projectList.forEach(entityManager::persist);
    }

    @Override
    public void teardown() {
        entityManager.createQuery("DELETE FROM Project").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE project AUTO_INCREMENT = 1")
            .executeUpdate();
    }
}
