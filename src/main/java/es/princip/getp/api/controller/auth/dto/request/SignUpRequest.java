package es.princip.getp.api.controller.auth.dto.request;

import es.princip.getp.domain.auth.application.command.SignUpCommand;
import es.princip.getp.api.validation.UserMemberType;
import es.princip.getp.domain.member.command.ServiceTermAgreementCommand;
import es.princip.getp.domain.member.model.Email;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.member.model.Password;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SignUpRequest(
    @NotNull String email,
    @NotNull String password,
    @NotNull String verificationCode,
    @NotNull @Valid List<ServiceTermAgreementRequest> serviceTerms,
    @UserMemberType MemberType memberType
) {

    public SignUpCommand toCommand() {
        Email email = Email.of(this.email);
        Password password = Password.of(this.password);
        String verificationCode = this.verificationCode;
        MemberType memberType = this.memberType;
        List<ServiceTermAgreementCommand> serviceTerms = this.serviceTerms.stream()
            .map(ServiceTermAgreementRequest::toCommand)
            .toList();

        return new SignUpCommand(
            email,
            password,
            verificationCode,
            serviceTerms,
            memberType
        );
    }
}