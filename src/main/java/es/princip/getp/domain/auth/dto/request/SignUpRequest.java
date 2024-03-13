package es.princip.getp.domain.auth.dto.request;

import es.princip.getp.domain.auth.dto.validator.annotation.Password;
import es.princip.getp.domain.member.domain.enums.MemberType;
import es.princip.getp.domain.serviceTerm.dto.reqeust.ServiceTermAgreementRequest;
import es.princip.getp.global.validator.annotation.Enum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SignUpRequest(
    @NotNull @Email String email,
    @NotNull @Password String password,
    @NotNull String verificationCode,
    @NotNull List<ServiceTermAgreementRequest> serviceTerms,
    @Enum MemberType memberType
) {
}