package es.princip.getp.persistence.adapter.like.project;

import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.persistence.support.DataLoader;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.fixture.like.ProjectLikeFixture.projectLike;

@RequiredArgsConstructor
public class ProjectLikeDataLoader implements DataLoader {

    private final EntityManager entityManager;
    private final ProjectLikePersistenceMapper mapper;

    @Override
    @Transactional
    public void load(final int size) {
        final List<ProjectLikeJpaEntity> projectLikeList = new ArrayList<>();
        LongStream.rangeClosed(1, size).forEach(memberId ->
            LongStream.rangeClosed(1, size).forEach(projectId ->
                projectLikeList.add(
                    mapper.mapToJpa(
                        projectLike(
                            new MemberId(memberId),
                            new ProjectId(projectId)
                        )
                    )
                )
            )
        );
        projectLikeList.forEach(entityManager::persist);
    }

    @Override
    public void teardown() {
        entityManager.createQuery("DELETE FROM ProjectLikeJpaEntity").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE project_like AUTO_INCREMENT = 1")
            .executeUpdate();
    }
}
