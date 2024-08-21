package es.princip.getp.application.projectMeeting.command;

import es.princip.getp.common.domain.MeetingSchedule;
import es.princip.getp.domain.member.command.domain.model.PhoneNumber;

public record ScheduleMeetingCommand(
    Long memberId,
    Long projectId,
    Long applicantId,
    String location,
    MeetingSchedule schedule,
    PhoneNumber phoneNumber,
    String description
) {
}
