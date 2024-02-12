package es.princip.getp.fixture;

import es.princip.getp.domain.project.dto.request.CreateProjectRequest;
import es.princip.getp.domain.project.enums.MeetingType;
import java.time.LocalDate;
import java.util.List;

public class ProjectFixture {

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
}
