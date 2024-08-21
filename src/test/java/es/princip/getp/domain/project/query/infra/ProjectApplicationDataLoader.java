package es.princip.getp.domain.project.query.infra;

import es.princip.getp.common.util.DataLoader;
import es.princip.getp.domain.project.command.domain.ProjectApplication;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.fixture.project.ProjectApplicationFixture.projectApplication;

@RequiredArgsConstructor
public class ProjectApplicationDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Override
    public void load(final int size) {
        final List<ProjectApplication> projectApplicationList = new ArrayList<>();
        LongStream.rangeClosed(1, size).forEach(projectId ->
            LongStream.rangeClosed(1, size).forEach(peopleId ->
                projectApplicationList.add(projectApplication(peopleId, projectId))
            )
        );
        projectApplicationList.forEach(entityManager::persist);
    }

    @Override
    public void teardown() {
        entityManager.createQuery("DELETE FROM ProjectApplication").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE project_application AUTO_INCREMENT = 1")
            .executeUpdate();
    }
}
