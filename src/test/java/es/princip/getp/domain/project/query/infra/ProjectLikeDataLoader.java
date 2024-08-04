package es.princip.getp.domain.project.query.infra;

import es.princip.getp.domain.project.command.domain.ProjectLike;
import es.princip.getp.infra.DataLoader;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

@RequiredArgsConstructor
public class ProjectLikeDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Transactional
    @Override
    public void load(final int size) {
        loadProjectLike(size);
    }

    private void loadProjectLike(final int size) {
        final List<ProjectLike> projectLikeList = new ArrayList<>();
        LongStream.rangeClosed(1, size).forEach(projectId ->
            LongStream.rangeClosed(1, size).forEach(peopleId ->
                projectLikeList.add(ProjectLike.of(peopleId, projectId))
            )
        );
        projectLikeList.forEach(entityManager::persist);
    }
}
