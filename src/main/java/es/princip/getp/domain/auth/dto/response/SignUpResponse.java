package es.princip.getp.domain.auth.dto.response;

import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.member.domain.enums.MemberType;
import es.princip.getp.domain.serviceTerm.dto.response.ServiceTermAgreementResponse;
import java.util.List;

public record SignUpResponse(
    String email,
    List<ServiceTermAgreementResponse> serviceTerms,
    MemberType memberType
) {
    public static SignUpResponse from(Member member) {
        return new SignUpResponse(
            member.getEmail(), 
            ServiceTermAgreementResponse.from(member.getServiceTermAgreements()), 
            member.getMemberType()
        );
    }
}