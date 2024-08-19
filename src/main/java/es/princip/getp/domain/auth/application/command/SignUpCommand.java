package es.princip.getp.domain.auth.application.command;

import es.princip.getp.domain.member.command.ServiceTermAgreementCommand;
import es.princip.getp.domain.member.model.Email;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.member.model.Password;

import java.util.List;

public record SignUpCommand(
    Email email,
    Password password,
    String verificationCode,
    List<ServiceTermAgreementCommand> serviceTerms,
    MemberType memberType
) {
}
