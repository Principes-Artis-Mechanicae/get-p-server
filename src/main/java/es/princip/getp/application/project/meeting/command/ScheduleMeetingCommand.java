package es.princip.getp.application.project.meeting.command;

import es.princip.getp.domain.common.model.MeetingSchedule;
import es.princip.getp.domain.common.model.PhoneNumber;
import es.princip.getp.domain.member.model.MemberId;

public record ScheduleMeetingCommand(
    MemberId memberId,
    Long projectId,
    Long applicantId,
    String location,
    MeetingSchedule schedule,
    PhoneNumber phoneNumber,
    String description
) {
}
