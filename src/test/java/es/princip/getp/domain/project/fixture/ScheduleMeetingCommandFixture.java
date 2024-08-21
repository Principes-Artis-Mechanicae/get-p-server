package es.princip.getp.domain.project.fixture;

import es.princip.getp.application.projectMeeting.command.ScheduleMeetingCommand;
import es.princip.getp.common.domain.MeetingSchedule;

import static es.princip.getp.domain.member.fixture.PhoneNumberFixture.phoneNumber;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleMeetingCommandFixture {
    public final static String LOCATION = "미팅 장소";
    public final static MeetingSchedule SCHEDULE = MeetingSchedule.of(
        LocalDate.of(2024, 8, 1),
        LocalTime.of(10, 0),
        LocalTime.of(11, 0)
    );
    public final static String DESCRIPTION = "미팅 요구사항";

    public static ScheduleMeetingCommand scheduleMeetingCommand(Long memberId, Long projectId, Long applicantId) {
        return new ScheduleMeetingCommand(
            memberId,
            projectId,
            applicantId,
            LOCATION,
            SCHEDULE,
            phoneNumber(),
            DESCRIPTION
        );
    }
}
