package es.princip.getp.domain.auth.application;

import es.princip.getp.domain.member.command.domain.model.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenService extends JwtTokenService {

    @Autowired
    public AccessTokenService(
        @Value("${spring.jwt.access-token.expire-time}") final Long expireTime,
        @Value("${spring.jwt.secret}") final String secretKey,
        final MemberRepository memberRepository
    ) {
        super(expireTime, secretKey, "Access", "Authorization", memberRepository);
    }
}
