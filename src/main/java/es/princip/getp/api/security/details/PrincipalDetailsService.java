package es.princip.getp.api.security.details;

import es.princip.getp.application.member.port.out.LoadMemberPort;
import es.princip.getp.domain.common.model.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final LoadMemberPort loadMemberPort;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return new PrincipalDetails(loadMemberPort.loadBy(Email.from(username)));
    }
}
