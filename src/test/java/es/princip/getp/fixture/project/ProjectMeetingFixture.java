package es.princip.getp.fixture.project;

import es.princip.getp.domain.common.model.MeetingSchedule;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.meeting.model.ProjectMeeting;

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
    
    public static ProjectMeeting projectMeeting(final Long projectId, final PeopleId applicantId) {
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
