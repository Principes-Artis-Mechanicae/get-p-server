package es.princip.getp.fixture.project;

import es.princip.getp.common.domain.Duration;
import es.princip.getp.common.domain.Hashtag;
import es.princip.getp.domain.project.command.domain.*;

import java.time.LocalDate;
import java.util.List;

public class ProjectFixture {

    public static Project project(final Long clientId, final ProjectStatus status) {
        return Project.builder()
            .clientId(clientId)
            .category(ProjectCategory.BACKEND)
            .attachmentFiles(List.of(
                AttachmentFile.from("https://example.com/attachment1"),
                AttachmentFile.from("https://example.com/attachment2")
            ))
            .payment(1_000_000L)
            .status(status)
            .title("프로젝트 제목")
            .description("프로젝트 설명")
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
}
