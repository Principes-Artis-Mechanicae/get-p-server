package es.princip.getp.persistence.adapter.project.commission;

import es.princip.getp.domain.project.commission.model.ProjectStatus;
import es.princip.getp.persistence.support.DataLoader;
import es.princip.getp.persistence.adapter.project.ProjectPersistenceMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.fixture.project.ProjectFixture.project;

@RequiredArgsConstructor
public class ProjectDataLoader implements DataLoader {

    private final ProjectPersistenceMapper mapper;
    private final EntityManager entityManager;

    @Override
    public void load(final int size) {
        final List<ProjectJpaEntity> projectList = LongStream.rangeClosed(1, size)
            .boxed()
            .flatMap(clientId -> Arrays.stream(ProjectStatus.values())
                .map(status -> mapper.mapToJpa(project(clientId, status)))
            )
            .toList();
        projectList.forEach(entityManager::persist);
    }

    @Override
    public void teardown() {
        entityManager.createQuery("DELETE FROM ProjectJpaEntity").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE project AUTO_INCREMENT = 1")
            .executeUpdate();
    }
}
