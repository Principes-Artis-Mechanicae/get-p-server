package es.princip.getp.domain.like.query.infra;

import es.princip.getp.common.util.DataLoader;
import es.princip.getp.domain.like.command.domain.project.ProjectLike;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

@RequiredArgsConstructor
public class ProjectLikeDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Override
    @Transactional
    public void load(final int size) {
        final List<ProjectLike> projectLikeList = new ArrayList<>();
        LongStream.rangeClosed(1, size).forEach(projectId ->
            LongStream.rangeClosed(1, size).forEach(peopleId ->
                projectLikeList.add(ProjectLike.of(peopleId, projectId))
            )
        );
        projectLikeList.forEach(entityManager::persist);
    }

    @Override
    public void teardown() {
        entityManager.createQuery("DELETE FROM ProjectLike").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE project_like AUTO_INCREMENT = 1")
            .executeUpdate();
    }
}
