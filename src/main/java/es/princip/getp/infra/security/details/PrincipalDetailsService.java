package es.princip.getp.infra.security.details;

import es.princip.getp.domain.member.domain.model.Email;
import es.princip.getp.domain.member.domain.model.Member;
import es.princip.getp.domain.member.domain.model.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(Email.of(username))
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new PrincipalDetails(member);
    }
}
