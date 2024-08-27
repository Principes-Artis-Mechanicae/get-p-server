package es.princip.getp.fixture.project;

import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectCategory;
import es.princip.getp.domain.project.commission.model.ProjectStatus;

import java.time.LocalDate;

import static es.princip.getp.fixture.common.HashtagFixture.hashtags;
import static es.princip.getp.fixture.project.AttachmentFileFixture.attachmentFiles;

public class ProjectFixture {

    public static final Long PAYMENT = 1_000_000L;
    public static final String TITLE = "프로젝트 제목";
    public static final String DESCRIPTION = "프로젝트 설명";

    private static final Project.ProjectBuilder builder = Project.builder()
        .category(ProjectCategory.BACKEND)
        .attachmentFiles(attachmentFiles())
        .payment(PAYMENT)
        .title(TITLE)
        .description(DESCRIPTION)
        .meetingType(MeetingType.IN_PERSON)
        .estimatedDuration(Duration.of(
            LocalDate.of(2024, 8, 1),
            LocalDate.of(2024, 8, 31)
        ))
        .hashtags(hashtags());

    public static Project project(final Long clientId, final ProjectStatus status) {
        return builder.clientId(clientId)
            .status(status)
            .applicationDuration(Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            ))
            .build();
    }

    public static Project project(final Long clientId, final ProjectStatus status, final Duration applicationDuration) {
        return builder.clientId(clientId)
            .status(status)
            .applicationDuration(applicationDuration)
            .build();
    }
}
