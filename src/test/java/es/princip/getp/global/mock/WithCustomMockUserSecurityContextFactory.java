package es.princip.getp.global.mock;

import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.global.security.details.PrincipalDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.LocalDateTime;
import java.util.List;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        String role = annotation.memberType().name();
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.builder()
            .memberId(annotation.memberId())
            .email(annotation.email())
            .memberType(annotation.memberType())
            .createdAt(now)
            .updatedAt(now)
            .build();
        Authentication auth = new UsernamePasswordAuthenticationToken(new PrincipalDetails(member), "",
            List.of(new SimpleGrantedAuthority(role)));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(auth);
        return context;
    }
}
