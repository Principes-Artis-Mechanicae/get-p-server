package es.princip.getp.application.project.meeting;

import es.princip.getp.application.project.meeting.dto.command.ScheduleMeetingCommand;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.commission.model.ProjectId;

import static es.princip.getp.fixture.member.PhoneNumberFixture.phoneNumber;
import static es.princip.getp.fixture.project.ProjectMeetingFixture.*;

class ScheduleMeetingCommandFixture {

    public static ScheduleMeetingCommand scheduleMeetingCommand(
        final MemberId memberId,
        final ProjectId projectId,
        final PeopleId applicantId
    ) {
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
