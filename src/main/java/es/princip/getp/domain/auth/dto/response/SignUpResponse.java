package es.princip.getp.domain.auth.dto.response;

import java.util.List;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.entity.MemberRoleType;
import es.princip.getp.domain.serviceTermAgreement.dto.response.ServiceTermAgreementResponse;
import jakarta.validation.constraints.NotNull;

public record SignUpResponse(
    @NotNull String email, 
    @NotNull List<ServiceTermAgreementResponse> serviceTerms, 
    @NotNull MemberRoleType roleType
) {
    public static SignUpResponse from(Member member) {
        return new SignUpResponse(
            member.getEmail(), 
            ServiceTermAgreementResponse.from(member.getServiceTermAgreements()), 
            member.getRoleType()
        );
    }
}