package es.princip.getp.domain.member.command.application.command;

import es.princip.getp.domain.auth.application.command.SignUpCommand;
import es.princip.getp.domain.member.command.domain.command.ServiceTermAgreementCommand;
import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.domain.member.command.domain.model.Password;

import java.util.List;

public record CreateMemberCommand(
    Email email,
    Password password,
    List<ServiceTermAgreementCommand> serviceTerms,
    MemberType memberType
) {

    public static CreateMemberCommand from(SignUpCommand command) {
        return new CreateMemberCommand(
            command.email(),
            command.password(),
            command.serviceTerms(),
            command.memberType()
        );
    }
}