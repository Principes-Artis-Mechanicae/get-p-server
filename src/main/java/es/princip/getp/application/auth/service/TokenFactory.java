package es.princip.getp.application.auth.service;

import es.princip.getp.api.controller.auth.dto.response.Token;
import es.princip.getp.domain.member.model.Member;
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

        refreshTokenService.cacheRefreshToken(member.getId(), refreshToken);

        return new Token(JwtTokenService.BEARER_TYPE, accessToken, refreshToken);
    }
}
