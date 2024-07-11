package es.princip.getp.infra.mock;

import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.infra.security.details.PrincipalDetails;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        String role = annotation.memberType().name();
        LocalDateTime now = LocalDateTime.now();
        Member member = Mockito.spy(Member.builder()
            .email(annotation.email())
            .memberType(annotation.memberType())
            .createdAt(now)
            .updatedAt(now)
            .build());
        given(member.getMemberId()).willReturn(annotation.memberId());
        Authentication auth = new UsernamePasswordAuthenticationToken(new PrincipalDetails(member), "",
            List.of(new SimpleGrantedAuthority(role)));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(auth);
        return context;
    }
}
