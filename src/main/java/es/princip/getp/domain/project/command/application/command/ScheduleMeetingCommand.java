package es.princip.getp.domain.project.command.application.command;

import es.princip.getp.domain.common.domain.MeetingSchedule;
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
