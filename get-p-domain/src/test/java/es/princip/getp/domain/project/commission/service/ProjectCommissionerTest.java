package es.princip.getp.domain.project.commission.service;

import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.common.infrastructure.StubClockHolder;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.common.service.ClockHolder;
import es.princip.getp.domain.project.commission.exception.ApplicationDurationNotBeforeEstimatedDurationException;
import es.princip.getp.domain.project.commission.exception.EndedApplicationDurationException;
import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectCategory;
import es.princip.getp.domain.project.commission.model.ProjectData;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static es.princip.getp.fixture.common.HashtagFixture.hashtags;
import static es.princip.getp.fixture.project.AttachmentFileFixture.attachmentFiles;
import static es.princip.getp.fixture.project.ProjectFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProjectCommissionerTest {

    private final ClockHolder clockHolder = new StubClockHolder(LocalDate.of(2024, 7, 1));
    private final ProjectCommissioner projectCommissioner = new ProjectCommissioner(clockHolder);

    @Test
    void 지원자_모집_기간이_오늘보다_이전일_수_없다() {
        final ProjectData data = projectData(
            Duration.of(
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 30)
            ),
            Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            )
        );

        assertThatThrownBy(() -> projectCommissioner.commissionProject(data))
            .isInstanceOf(EndedApplicationDurationException.class);
    }

    @Test
    void 지원자_모집_기간이_오늘일_수_있다() {
        final ProjectData data = projectData(
            Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            ),
            Duration.of(
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 8, 31)
            )
        );

        final Project project = projectCommissioner.commissionProject(data);

        assertThat(project).usingRecursiveComparison().isNotNull();
    }

    @Test
    void 지원자_모집_기간은_예상_작업_기간보다_빠를_수_없다() {
        final ProjectData data = projectData(
            Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            ),
            Duration.of(
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 30)
            )
        );

        assertThatThrownBy(() -> projectCommissioner.commissionProject(data))
            .isInstanceOf(ApplicationDurationNotBeforeEstimatedDurationException.class);
    }

    private ProjectData projectData(final Duration applicationDuration, final Duration estimatedDuration) {
        return new ProjectData(
            TITLE,
            PAYMENT,
            RECRUITMENT_COUNT,
            applicationDuration,
            estimatedDuration,
            DESCRIPTION,
            MeetingType.IN_PERSON,
            ProjectCategory.BACKEND,
            new ClientId(1L),
            attachmentFiles(),
            hashtags()
        );
    }
}