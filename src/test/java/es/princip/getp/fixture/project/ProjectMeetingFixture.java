package es.princip.getp.fixture.project;

import es.princip.getp.common.domain.MeetingSchedule;
import es.princip.getp.domain.project.command.domain.ProjectMeeting;

import java.time.LocalDate;
import java.time.LocalTime;

import static es.princip.getp.fixture.member.PhoneNumberFixture.phoneNumber;

public class ProjectMeetingFixture {

    public final static String LOCATION = "미팅 장소";
    public final static MeetingSchedule SCHEDULE = MeetingSchedule.of(
        LocalDate.of(2024, 8, 1),
        LocalTime.of(10, 0),
        LocalTime.of(11, 0)
    );
    public final static String DESCRIPTION = "미팅 요구사항";
    
    public static ProjectMeeting projectMeeting(final Long projectId, final Long applicantId) {
        return ProjectMeeting.builder()
            .projectId(projectId)
            .applicantId(applicantId)
            .location(LOCATION)
            .schedule(SCHEDULE)
            .phoneNumber(phoneNumber())
            .description(DESCRIPTION)
            .build();
    }
}
