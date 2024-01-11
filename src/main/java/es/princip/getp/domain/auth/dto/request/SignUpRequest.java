package es.princip.getp.domain.auth.dto.request;

import java.util.List;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.entity.MemberRoleType;
import es.princip.getp.domain.serviceTermAgreement.dto.request.ServiceTermAgreementRequest;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
    @NotNull String email, 
    @NotNull String password, 
    @NotNull List<ServiceTermAgreementRequest> serviceTerms, 
    @NotNull MemberRoleType roleType
) {
    public Member toEntity() {
        return Member.builder().email(email).password(password).roleType(roleType).build();
    }
}