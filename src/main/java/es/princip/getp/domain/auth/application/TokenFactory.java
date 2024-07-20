package es.princip.getp.domain.auth.application;

import es.princip.getp.domain.auth.presentation.dto.response.Token;
import es.princip.getp.domain.member.command.domain.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenFactory {

    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    public Token generateToken(final Member member) {
        final String accessToken = accessTokenService.generateJwtToken(member);
        final String refreshToken = refreshTokenService.generateJwtToken(member);

        refreshTokenService.cacheRefreshToken(member.getMemberId(), refreshToken);

        return new Token(JwtTokenService.BEARER_TYPE, accessToken, refreshToken);
    }
}
