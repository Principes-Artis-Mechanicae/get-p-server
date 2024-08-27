package es.princip.getp.application.project.meeting.command;

import es.princip.getp.common.domain.MeetingSchedule;
import es.princip.getp.domain.member.model.PhoneNumber;

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
