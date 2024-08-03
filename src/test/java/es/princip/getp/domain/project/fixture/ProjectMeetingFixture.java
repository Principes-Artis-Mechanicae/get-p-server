package es.princip.getp.domain.project.fixture;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import es.princip.getp.domain.common.domain.MeetingSchedule;
import es.princip.getp.domain.member.fixture.PhoneNumberFixture;
import es.princip.getp.domain.project.command.domain.ProjectMeeting;

public class ProjectMeetingFixture {
    public final static Long projectId = 1L;
    public final static Long applicantId = 1L;
    public final static String meetingLocation = "디스코드";
    public final static List<MeetingSchedule> meetingSchedules = List.of(
            MeetingSchedule.of(LocalDate.of(2024, 8, 1), LocalTime.of(10, 0), LocalTime.of(11, 0)),
            MeetingSchedule.of(LocalDate.of(2024, 8, 2), LocalTime.of(14, 0), LocalTime.of(15, 0)),
            MeetingSchedule.of(LocalDate.of(2024, 8, 3), LocalTime.of(9, 0), LocalTime.of(10, 0))
        );
    public final static String description = "AI 개발 백엔드 기술스택 관련해서";
    
    public static ProjectMeeting projectMeeting() {
        return ProjectMeeting.builder()
            .projectId(projectId)
            .applicantId(applicantId)
            .meetingLocation(meetingLocation)
            .meetingSchedules(meetingSchedules)
            .phoneNumber(PhoneNumberFixture.phoneNumber())
            .description(description)
        .build();
    }
}
