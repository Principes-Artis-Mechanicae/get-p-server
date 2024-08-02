package es.princip.getp.domain.project.command.application.command;

import java.util.List;

import es.princip.getp.domain.common.domain.MeetingSchedule;
import es.princip.getp.domain.member.command.domain.model.PhoneNumber;

public record ScheduleMeetingCommand(
    Long memberId,
    Long projectId,
    Long applicantId,
    String meetingLocation,
    List<MeetingSchedule> meetingSchedules,
    PhoneNumber contactPhoneNumber,
    String description
) {
}
