package es.princip.getp.domain.project.query.infra;

import es.princip.getp.domain.project.command.domain.ProjectApplication;
import es.princip.getp.infra.DataLoader;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.domain.project.fixture.ProjectApplicationFixture.projectApplication;

@RequiredArgsConstructor
public class ProjectApplicationDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Transactional
    @Override
    public void load(final int size) {
        loadProjectApplication(size);
    }

    private void loadProjectApplication(final int size) {
        final List<ProjectApplication> projectApplicationList = new ArrayList<>();
        LongStream.rangeClosed(1, size).forEach(projectId ->
            LongStream.rangeClosed(1, size).forEach(peopleId ->
                projectApplicationList.add(projectApplication(peopleId, projectId))
            )
        );

        projectApplicationList.forEach(entityManager::persist);
    }
}
