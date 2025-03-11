package es.princip.getp.application.auth.command;

import es.princip.getp.domain.common.model.Email;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.member.model.Password;
import es.princip.getp.domain.member.model.ServiceTermAgreementData;

import java.util.Set;

public record SignUpCommand(
    Email email,
    Password password,
    String verificationCode,
    Set<ServiceTermAgreementData> serviceTerms,
    MemberType memberType
) {
}
