package es.princip.getp.fixture;

import es.princip.getp.domain.client.domain.entity.Client;
import es.princip.getp.domain.project.domain.entity.Project;
import es.princip.getp.domain.project.domain.enums.MeetingType;
import es.princip.getp.domain.project.dto.request.CreateProjectRequest;

import java.time.LocalDate;
import java.util.List;

import static es.princip.getp.domain.client.fixture.ClientFixture.createClient;

public class ProjectFixture {
    public static Long PROJECT_ID = 1L;
    public static String TITLE = "경북대학교 제1회 공모전";
    public static long PAYMENT = 10000;
    public static MeetingType MEETING_TYPE = MeetingType.REMOTE;
    public static String DESCRIPTION = "1등하면 만원 드립니다.";
    public static LocalDate APPLICATION_DEADLINE = LocalDate.now();
    public static LocalDate ESTIMATED_START_DATE = LocalDate.now().plusDays(7);
    public static LocalDate ESTIMATED_END_DATE = LocalDate.now().plusDays(14);
    public static List<String> ATTACHMENT_URIS =
        List.of("https://example.com/test1.png", "https://example.com/test2.png");
    public static List<String> HASHTAGS = List.of("#경북대학교", "#컴퓨터학부");

    public static CreateProjectRequest createProjectRequest() {
        return new CreateProjectRequest(TITLE, PAYMENT, APPLICATION_DEADLINE, ESTIMATED_START_DATE,
            ESTIMATED_END_DATE, DESCRIPTION, MEETING_TYPE, ATTACHMENT_URIS, HASHTAGS);
    }

    public static Project createProject(Client client) {
        return Project.builder()
            .projectId(PROJECT_ID)
            .title(TITLE)
            .payment(PAYMENT)
            .applicationDeadline(APPLICATION_DEADLINE)
            .estimatedStartDate(ESTIMATED_START_DATE)
            .estimatedEndDate(ESTIMATED_END_DATE)
            .description(DESCRIPTION)
            .meetingType(MEETING_TYPE)
            .attachmentUris(ATTACHMENT_URIS)
            .hashtags(HASHTAGS)
            .client(client)
            .build();
    }

    public static Project createProject() {
        return Project.builder()
            .projectId(PROJECT_ID)
            .title(TITLE)
            .payment(PAYMENT)
            .applicationDeadline(APPLICATION_DEADLINE)
            .estimatedStartDate(ESTIMATED_START_DATE)
            .estimatedEndDate(ESTIMATED_END_DATE)
            .description(DESCRIPTION)
            .meetingType(MEETING_TYPE)
            .attachmentUris(ATTACHMENT_URIS)
            .hashtags(HASHTAGS)
            .client(createClient())
            .build();
    }
}
