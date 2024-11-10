package es.princip.getp.fixture.project;

import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.project.commission.model.*;

import java.time.LocalDate;

import static es.princip.getp.fixture.common.HashtagFixture.hashtags;
import static es.princip.getp.fixture.project.AttachmentFileFixture.attachmentFiles;

public class ProjectFixture {

    public static final Long PAYMENT = 1_000_000L;
    public static final Long RECRUITMENT_COUNT = 1L;
    public static final String TITLE = "프로젝트 제목";
    public static final String DESCRIPTION = "프로젝트 설명";
    public static final LocalDate APPLICATION_START_DATE = LocalDate.of(2024, 7, 1);
    public static final LocalDate APPLICATION_END_DATE = LocalDate.of(2024, 7, 31);
    public static final LocalDate ESTIMATED_START_DATE = LocalDate.of(2024, 8, 1);
    public static final LocalDate ESTIMATED_END_DATE = LocalDate.of(2024, 8, 31);

    private static final Project.ProjectBuilder builder = Project.builder()
        .category(ProjectCategory.BACKEND)
        .attachmentFiles(attachmentFiles())
        .payment(PAYMENT)
        .recruitmentCount(RECRUITMENT_COUNT)
        .title(TITLE)
        .description(DESCRIPTION)
        .meetingType(MeetingType.IN_PERSON)
        .estimatedDuration(Duration.of(
            ESTIMATED_START_DATE,
            ESTIMATED_END_DATE
        ))
        .hashtags(hashtags());

    public static Project project(
        final ProjectId projectId,
        final ClientId clientId,
        final ProjectStatus status
    ) {
        return builder.id(projectId)
            .clientId(clientId)
            .status(status)
            .applicationDuration(Duration.of(
                APPLICATION_START_DATE,
                APPLICATION_END_DATE
            ))
            .build();
    }

    public static Project project(final ClientId clientId, final ProjectStatus status) {
        return builder.clientId(clientId)
            .status(status)
            .applicationDuration(Duration.of(
                APPLICATION_START_DATE,
                APPLICATION_END_DATE
            ))
            .build();
    }

    public static Project project(
        final ClientId clientId,
        final ProjectStatus status,
        final Duration applicationDuration
    ) {
        return builder.clientId(clientId)
            .status(status)
            .applicationDuration(applicationDuration)
            .build();
    }
}
