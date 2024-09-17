package es.princip.getp.application.project.meeting;

import es.princip.getp.application.project.meeting.command.ScheduleMeetingCommand;
import es.princip.getp.domain.member.model.MemberId;

import static es.princip.getp.fixture.member.PhoneNumberFixture.phoneNumber;
import static es.princip.getp.fixture.project.ProjectMeetingFixture.*;

class ScheduleMeetingCommandFixture {

    public static ScheduleMeetingCommand scheduleMeetingCommand(
        final MemberId memberId,
        final Long projectId,
        final Long applicantId
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
