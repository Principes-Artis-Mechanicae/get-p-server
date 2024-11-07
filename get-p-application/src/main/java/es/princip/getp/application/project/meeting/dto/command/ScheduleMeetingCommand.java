package es.princip.getp.application.project.meeting.dto.command;

import es.princip.getp.domain.common.model.MeetingSchedule;
import es.princip.getp.domain.common.model.PhoneNumber;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.commission.model.ProjectId;

public record ScheduleMeetingCommand(
    MemberId memberId,
    ProjectId projectId,
    PeopleId applicantId,
    String location,
    MeetingSchedule schedule,
    PhoneNumber phoneNumber,
    String description
) {
}
