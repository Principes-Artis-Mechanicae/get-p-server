package es.princip.getp.infra.security.details;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import es.princip.getp.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new PrincipalDetails(memberService.getByEmail(username));
    }
}
