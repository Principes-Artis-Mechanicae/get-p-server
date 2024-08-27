package es.princip.getp.fixture.project;

import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.common.model.Hashtag;
import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectCategory;
import es.princip.getp.domain.project.commission.model.ProjectStatus;

import java.time.LocalDate;
import java.util.List;

public class ProjectFixture {

    public static final Long PAYMENT = 1_000_000L;
    public static final String TITLE = "프로젝트 제목";
    public static final String DESCRIPTION = "프로젝트 설명";

    public static Project project(final Long clientId, final ProjectStatus status) {
        return Project.builder()
            .clientId(clientId)
            .category(ProjectCategory.BACKEND)
            .attachmentFiles(List.of(
                AttachmentFile.from("https://example.com/attachment1"),
                AttachmentFile.from("https://example.com/attachment2")
            ))
            .payment(PAYMENT)
            .status(status)
            .title(TITLE)
            .description(DESCRIPTION)
            .meetingType(MeetingType.IN_PERSON)
            .applicationDuration(Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            ))
            .estimatedDuration(Duration.of(
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 8, 31)
            ))
            .hashtags(List.of(
                Hashtag.from("Java"),
                Hashtag.from("Spring Boot")
            ))
            .build();
    }

    public static Project project(final Long clientId, final ProjectStatus status, final Duration applicationDuration) {
        return Project.builder()
            .clientId(clientId)
            .category(ProjectCategory.BACKEND)
            .attachmentFiles(List.of(
                AttachmentFile.from("https://example.com/attachment1"),
                AttachmentFile.from("https://example.com/attachment2")
            ))
            .payment(PAYMENT)
            .status(status)
            .title(TITLE)
            .description(DESCRIPTION)
            .meetingType(MeetingType.IN_PERSON)
            .applicationDuration(applicationDuration)
            .estimatedDuration(Duration.of(
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 8, 31)
            ))
            .hashtags(List.of(
                Hashtag.from("Java"),
                Hashtag.from("Spring Boot")
            ))
            .build();
    }
}
