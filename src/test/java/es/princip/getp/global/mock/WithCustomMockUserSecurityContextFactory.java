package es.princip.getp.global.mock;

import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.global.security.details.PrincipalDetails;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        String email = annotation.email();
        String role = annotation.memberType().name();
        Member member = Member.builder()
            .email(email)
            .memberType(annotation.memberType())
            .build();
        Authentication auth = new UsernamePasswordAuthenticationToken(new PrincipalDetails(member), "",
            List.of(new SimpleGrantedAuthority(role)));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(auth);
        return context;
    }
}
