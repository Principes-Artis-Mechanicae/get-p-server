package es.princip.getp.domain.auth.dto.request;

import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.member.domain.enums.MemberType;
import es.princip.getp.domain.serviceTerm.dto.reqeust.ServiceTermAgreementRequest;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
    @NotNull String email, 
    @NotNull String password, 
    @NotNull List<ServiceTermAgreementRequest> serviceTerms, 
    @NotNull MemberType memberType
) {
    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
            .email(email)
            .password(passwordEncoder.encode(password))
            .memberType(memberType)
            .build();
    }
}