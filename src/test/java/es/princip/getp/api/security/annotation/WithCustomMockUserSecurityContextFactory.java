package es.princip.getp.api.security.annotation;

import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.domain.member.model.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.LocalDateTime;
import java.util.List;

import static es.princip.getp.fixture.member.ProfileImageFixture.profileImage;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(final WithCustomMockUser annotation) {
        final Email email = Email.of(annotation.email());
        final Password password = Password.of(annotation.password());
        final Nickname nickname = Nickname.of(annotation.nickname());
        final PhoneNumber phoneNumber = PhoneNumber.of(annotation.phoneNumber());
        final ProfileImage profileImage = profileImage(annotation.memberId());
        final MemberType memberType = annotation.memberType();
        final LocalDateTime now = LocalDateTime.now();

        final Member member = spy(Member.of(email, password, memberType));
        given(member.getMemberId()).willReturn(annotation.memberId());
        given(member.getNickname()).willReturn(nickname);
        given(member.getPhoneNumber()).willReturn(phoneNumber);
        given(member.getProfileImage()).willReturn(profileImage);
        given(member.getCreatedAt()).willReturn(now);
        given(member.getUpdatedAt()).willReturn(now);

        final String role = annotation.memberType().name();
        final Authentication auth = new UsernamePasswordAuthenticationToken(new PrincipalDetails(member), "",
            List.of(new SimpleGrantedAuthority(role)));
        final SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(auth);

        return context;
    }
}
