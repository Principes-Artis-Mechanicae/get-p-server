package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.domain.project.apply.model.TeammateStatus;
import es.princip.getp.persistence.adapter.common.DurationJpaVO;
import es.princip.getp.persistence.adapter.project.apply.model.IndividualProjectApplicationJpaEntity;
import es.princip.getp.persistence.adapter.project.apply.model.ProjectApplicationJpaEntity;
import es.princip.getp.persistence.adapter.project.apply.model.TeamProjectApplicationJpaEntity;
import es.princip.getp.persistence.adapter.project.apply.model.TeammateJpaEntity;
import es.princip.getp.persistence.support.DataLoader;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.domain.project.apply.model.ProjectApplicationStatus.COMPLETED;
import static es.princip.getp.fixture.project.ProjectApplicationFixture.DESCRIPTION;

@RequiredArgsConstructor
public class ProjectApplicationDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Override
    public void load(final int size) {
        final List<ProjectApplicationJpaEntity> projectApplicationList = new ArrayList<>();
        LongStream.rangeClosed(1, size / 2).forEach(id ->
            projectApplicationList.add(individualProjectApplication(id, id))
        );
        LongStream.rangeClosed(size / 2 + 1, size).forEach(id ->
            projectApplicationList.add(teamProjectApplication(id, id, 2)) // TODO: 현재 같은 피플이 중복 지원 중
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

    static ProjectApplicationJpaEntity individualProjectApplication(
        final Long applicantId,
        final Long projectId
    ) {
        return IndividualProjectApplicationJpaEntity.builder()
            .applicantId(applicantId)
            .projectId(projectId)
            .expectedDuration(new DurationJpaVO(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            ))
            .status(COMPLETED)
            .description(DESCRIPTION)
            .attachmentFiles(List.of("https://example.com/attachment1"))
            .build();
    }

    static ProjectApplicationJpaEntity teamProjectApplication(
        final Long applicantId,
        final Long projectId,
        final int teamSize
    ) {
        final TeamProjectApplicationJpaEntity application = TeamProjectApplicationJpaEntity.builder()
            .applicantId(applicantId)
            .projectId(projectId)
            .expectedDuration(new DurationJpaVO(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            ))
            .status(COMPLETED)
            .description(DESCRIPTION)
            .attachmentFiles(List.of("https://example.com/attachment1"))
            .build();
        LongStream.rangeClosed(applicantId + 1, applicantId + teamSize - 1).forEach(peopleId ->
            application.addTeammate(teammate(peopleId, application))
        );
        return application;
    }

    private static TeammateJpaEntity teammate(
        final Long peopleId,
        final TeamProjectApplicationJpaEntity application
    ) {
        return TeammateJpaEntity.builder()
            .peopleId(peopleId)
            .status(TeammateStatus.APPROVED)
            .application(application)
            .build();
    }
}
