package es.princip.getp.api.controller.auth.dto.request;

import es.princip.getp.api.validation.UserMemberType;
import es.princip.getp.domain.common.model.EmailPattern;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.member.model.PasswordPattern;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record SignUpRequest(
    @NotNull @EmailPattern String email,
    @NotNull @PasswordPattern String password,
    @NotNull String verificationCode,
    @NotNull @Valid Set<ServiceTermAgreementRequest> serviceTerms,
    @UserMemberType MemberType memberType
) {
}