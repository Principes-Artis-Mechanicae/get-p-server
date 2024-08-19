package es.princip.getp.application.member.command;

import es.princip.getp.domain.auth.application.command.SignUpCommand;
import es.princip.getp.domain.member.command.ServiceTermAgreementCommand;
import es.princip.getp.domain.member.model.Email;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.member.model.Password;

import java.util.List;

public record CreateMemberCommand(
    Email email,
    Password password,
    List<ServiceTermAgreementCommand> serviceTerms,
    MemberType memberType
) {

    public static CreateMemberCommand from(final SignUpCommand command) {
        return new CreateMemberCommand(
            command.email(),
            command.password(),
            command.serviceTerms(),
            command.memberType()
        );
    }
}