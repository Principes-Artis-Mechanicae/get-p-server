package es.princip.getp.persistence.adapter.project.commission;

import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.ProjectCategory;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import es.princip.getp.persistence.adapter.common.DurationJpaVO;
import es.princip.getp.persistence.support.DataLoader;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static es.princip.getp.fixture.project.ProjectFixture.*;

@RequiredArgsConstructor
public class ProjectDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Override
    public void load(final int size) {
        final List<ProjectStatus> statuses = List.of(ProjectStatus.values());
        final int statusCount = size / statuses.size();
        final List<ProjectJpaEntity> projectList = IntStream.range(0, statuses.size())
            .mapToObj(statusIdx -> LongStream.range(0, statusCount)
                .mapToObj(clientId -> project(
                    statusIdx * statusCount + clientId + 1,
                    statuses.get(statusIdx)
                )))
            .flatMap(s -> s)
            .toList();
        projectList.forEach(entityManager::persist);
    }

    @Override
    public void teardown() {
        entityManager.createQuery("DELETE FROM ProjectJpaEntity").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE project AUTO_INCREMENT = 1")
            .executeUpdate();
    }

    private ProjectJpaEntity project(final Long clientId, final ProjectStatus status) {
        return ProjectJpaEntity.builder()
            .title(TITLE)
            .payment(PAYMENT)
            .recruitmentCount(RECRUITMENT_COUNT)
            .applicationDuration(new DurationJpaVO(
                APPLICATION_START_DATE,
                APPLICATION_END_DATE))
            .estimatedDuration(new DurationJpaVO(
                ESTIMATED_START_DATE,
                ESTIMATED_END_DATE))
            .description(DESCRIPTION)
            .meetingType(MeetingType.IN_PERSON)
            .category(ProjectCategory.BACKEND)
            .status(status)
            .clientId(clientId)
            .build();
    }
}
