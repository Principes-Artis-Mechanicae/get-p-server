package es.princip.getp.domain.auth.dto.response;

import java.util.List;
import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.member.domain.enums.MemberType;
import es.princip.getp.domain.serviceTerm.dto.response.ServiceTermAgreementResponse;
import jakarta.validation.constraints.NotNull;

public record SignUpResponse(
    @NotNull String email, 
    @NotNull List<ServiceTermAgreementResponse> serviceTerms, 
    @NotNull MemberType memberType
) {
    public static SignUpResponse from(Member member) {
        return new SignUpResponse(
            member.getEmail(), 
            ServiceTermAgreementResponse.from(member.getServiceTermAgreements()), 
            member.getMemberType()
        );
    }
}