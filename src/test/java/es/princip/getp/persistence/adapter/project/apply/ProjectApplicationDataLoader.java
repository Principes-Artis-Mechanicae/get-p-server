package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.persistence.support.DataLoader;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.fixture.project.ProjectApplicationFixture.individualProjectApplication;
import static es.princip.getp.fixture.project.ProjectApplicationFixture.teamProjectApplication;

@RequiredArgsConstructor
public class ProjectApplicationDataLoader implements DataLoader {

    private final ProjectApplicationPersistenceMapper mapper;
    private final EntityManager entityManager;

    @Override
    public void load(final int size) {
        final List<ProjectApplicationJpaEntity> projectApplicationList = new ArrayList<>();
        LongStream.rangeClosed(1, size / 2).forEach(projectId ->
            LongStream.rangeClosed(1, size).forEach(peopleId ->
                projectApplicationList.add(
                    mapper.mapToJpa(
                        individualProjectApplication(
                            new PeopleId(peopleId),
                            new ProjectId(projectId)
                        )
                    )
                )
            )
        );
        LongStream.rangeClosed(size / 2 + 1, size).forEach(projectId ->
            LongStream.rangeClosed(1, size).forEach(peopleId ->
                projectApplicationList.add(
                    mapper.mapToJpa(
                        teamProjectApplication(
                            new PeopleId(peopleId),
                            new ProjectId(projectId)
                        )
                    )
                )
            )
        );
        projectApplicationList.forEach(entityManager::persist);
    }

    @Override
    public void teardown() {
        entityManager.createQuery("DELETE FROM ProjectApplicationJpaEntity").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE individual_project_application AUTO_INCREMENT = 1")
            .executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE team_project_application AUTO_INCREMENT = 1")
            .executeUpdate();
    }
}
