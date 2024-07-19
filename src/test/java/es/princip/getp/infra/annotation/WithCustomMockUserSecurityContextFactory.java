package es.princip.getp.infra.annotation;

import es.princip.getp.domain.member.command.domain.model.*;
import es.princip.getp.infra.security.details.PrincipalDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        Email email = Email.of(annotation.email());
        Password password = Password.of(annotation.password());
        Nickname nickname = Nickname.of(annotation.nickname());
        PhoneNumber phoneNumber = PhoneNumber.of(annotation.phoneNumber());
        MemberType memberType = annotation.memberType();
        LocalDateTime now = LocalDateTime.now();

        Member member = spy(Member.of(email, password, memberType));
        given(member.getMemberId()).willReturn(annotation.memberId());
        given(member.getNickname()).willReturn(nickname);
        given(member.getPhoneNumber()).willReturn(phoneNumber);
        given(member.getCreatedAt()).willReturn(now);
        given(member.getUpdatedAt()).willReturn(now);

        String role = annotation.memberType().name();
        Authentication auth = new UsernamePasswordAuthenticationToken(new PrincipalDetails(member), "",
            List.of(new SimpleGrantedAuthority(role)));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(auth);

        return context;
    }
}
