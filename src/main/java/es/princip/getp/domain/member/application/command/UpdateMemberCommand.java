package es.princip.getp.domain.member.application.command;

import es.princip.getp.domain.client.application.command.CreateClientCommand;
import es.princip.getp.domain.client.application.command.UpdateClientCommand;
import es.princip.getp.domain.member.domain.model.Nickname;
import es.princip.getp.domain.member.domain.model.PhoneNumber;
import es.princip.getp.domain.people.command.application.command.CreatePeopleCommand;
import es.princip.getp.domain.people.command.application.command.UpdatePeopleCommand;

public record UpdateMemberCommand(
    Long memberId,
    Nickname nickname,
    PhoneNumber phoneNumber
) {
    public static UpdateMemberCommand from(CreatePeopleCommand command) {
        return new UpdateMemberCommand(
            command.memberId(),
            command.nickname(),
            command.phoneNumber()
        );
    }

    public static UpdateMemberCommand from(UpdatePeopleCommand command) {
        return new UpdateMemberCommand(
            command.memberId(),
            command.nickname(),
            command.phoneNumber()
        );
    }

    public static UpdateMemberCommand from(CreateClientCommand command) {
        return new UpdateMemberCommand(
            command.memberId(),
            command.nickname(),
            command.phoneNumber()
        );
    }

    public static UpdateMemberCommand from(UpdateClientCommand command) {
        return new UpdateMemberCommand(
            command.memberId(),
            command.nickname(),
            command.phoneNumber()
        );
    }
}
