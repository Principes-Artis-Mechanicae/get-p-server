package es.princip.getp.application.auth.service;

import es.princip.getp.application.auth.exception.InvalidTokenException;
import es.princip.getp.application.member.port.out.LoadMemberPort;
import es.princip.getp.domain.auth.RefreshToken;
import es.princip.getp.domain.member.model.MemberId;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RefreshTokenService extends JwtTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public RefreshTokenService(
        @Value("${spring.jwt.refresh-token.expire-time}") final Long expireTime,
        @Value("${spring.jwt.secret}") final String secretKey,
        final LoadMemberPort loadMemberPort,
        final RefreshTokenRepository refreshTokenRepository
    ) {
        super(expireTime, secretKey,"Refresh", "Refresh-Token", loadMemberPort);
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public void cacheRefreshToken(final MemberId memberId, final String refreshToken) {
        refreshTokenRepository.save(new RefreshToken(memberId, refreshToken, expireTime));
    }

    @Override
    protected Claims validateAndParseToken(final String token) {
        if (!refreshTokenRepository.existsByRefreshToken(token)) {
            throw new InvalidTokenException(tokenType);
        }
        return super.validateAndParseToken(token);
    }
}
