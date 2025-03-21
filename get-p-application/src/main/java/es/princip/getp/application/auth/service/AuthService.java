package es.princip.getp.application.auth.service;

import es.princip.getp.application.auth.dto.response.Token;
import es.princip.getp.application.auth.exception.IncorrectEmailOrPasswordException;
import es.princip.getp.domain.member.model.Member;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenFactory tokenFactory;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public Token login(final String email, final String password) {
        try {
            final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
            final Authentication authentication = authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);
            final PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            final Member member = principalDetails.getMember();

            return tokenFactory.generateToken(member);
        } catch (final Exception e) {
            throw new IncorrectEmailOrPasswordException();
        }
    }

    @Transactional
    public Token reissueAccessToken(final HttpServletRequest servletRequest) {
        final String refreshToken = refreshTokenService.resolveToken(servletRequest);
        final Authentication authentication = refreshTokenService.getAuthentication(refreshToken);
        final PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        final Member member = principalDetails.getMember();

        return tokenFactory.generateToken(member);
    }
}
