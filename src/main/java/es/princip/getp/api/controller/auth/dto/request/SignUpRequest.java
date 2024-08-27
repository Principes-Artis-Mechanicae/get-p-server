package es.princip.getp.api.controller.auth.dto.request;

import es.princip.getp.api.validation.UserMemberType;
import es.princip.getp.domain.member.model.MemberType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record SignUpRequest(
    @NotNull String email,
    @NotNull String password,
    @NotNull String verificationCode,
    @NotNull @Valid Set<ServiceTermAgreementRequest> serviceTerms,
    @UserMemberType MemberType memberType
) {
}