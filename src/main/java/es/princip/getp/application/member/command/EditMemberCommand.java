package es.princip.getp.application.member.command;

import es.princip.getp.application.client.command.EditClientCommand;
import es.princip.getp.application.client.command.RegisterClientCommand;
import es.princip.getp.application.people.command.EditPeopleCommand;
import es.princip.getp.application.people.command.RegisterPeopleCommand;
import es.princip.getp.domain.common.model.PhoneNumber;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.member.model.Nickname;

public record EditMemberCommand(
    MemberId memberId,
    Nickname nickname,
    PhoneNumber phoneNumber
) {
    public static EditMemberCommand from(final RegisterPeopleCommand command) {
        return new EditMemberCommand(
            command.memberId(),
            command.nickname(),
            command.phoneNumber()
        );
    }

    public static EditMemberCommand from(final EditPeopleCommand command) {
        return new EditMemberCommand(
            command.memberId(),
            command.nickname(),
            command.phoneNumber()
        );
    }

    public static EditMemberCommand from(final RegisterClientCommand command) {
        return new EditMemberCommand(
            command.member().getId(),
            command.nickname(),
            command.phoneNumber()
        );
    }

    public static EditMemberCommand from(final EditClientCommand command) {
        return new EditMemberCommand(
            command.memberId(),
            command.nickname(),
            command.phoneNumber()
        );
    }
}
