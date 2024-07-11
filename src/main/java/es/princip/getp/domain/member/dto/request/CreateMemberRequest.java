package es.princip.getp.domain.member.dto.request;

import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.auth.annotation.Password;
import es.princip.getp.domain.member.domain.MemberType;
import es.princip.getp.domain.member.validator.annotation.UserMemberType;
import es.princip.getp.domain.serviceTerm.dto.reqeust.ServiceTermAgreementRequest;
import es.princip.getp.global.validator.annotation.Enum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;

public record CreateMemberRequest(
    @NotNull @Email String email,
    @NotNull @Password String password, // 비밀 번호는 인코딩 되어야 한다.
    @NotNull List<ServiceTermAgreementRequest> serviceTerms,
    @Enum @UserMemberType MemberType memberType
) {

    public static CreateMemberRequest from(SignUpRequest request, PasswordEncoder encoder) {
        return new CreateMemberRequest(
            request.email(),
            encoder.encode(request.password()),
            request.serviceTerms(),
            request.memberType()
        );
    }
}