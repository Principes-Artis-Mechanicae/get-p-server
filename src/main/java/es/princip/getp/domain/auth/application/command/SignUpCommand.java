package es.princip.getp.domain.auth.application.command;

import es.princip.getp.domain.member.domain.command.ServiceTermAgreementCommand;
import es.princip.getp.domain.member.domain.model.Email;
import es.princip.getp.domain.member.domain.model.MemberType;
import es.princip.getp.domain.member.domain.model.Password;

import java.util.List;

public record SignUpCommand(
    Email email,
    Password password,
    String verificationCode,
    List<ServiceTermAgreementCommand> serviceTerms,
    MemberType memberType
) {
}
