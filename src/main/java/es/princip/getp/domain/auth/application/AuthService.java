package es.princip.getp.domain.auth.application;

import es.princip.getp.domain.auth.exception.LoginErrorCode;
import es.princip.getp.domain.auth.presentation.dto.request.LoginRequest;
import es.princip.getp.domain.auth.presentation.dto.response.Token;
import es.princip.getp.domain.member.command.domain.model.Member;
import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.security.details.PrincipalDetails;
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
    public Token login(final LoginRequest request) {
        final String email = request.email();
        final String password = request.password();

        try {
            final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
            final Authentication authentication = authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);
            final PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            final Member member = principalDetails.getMember();

            return tokenFactory.generateToken(member);
        } catch (Exception e) {
            throw new BusinessLogicException(LoginErrorCode.INCORRECT_EMAIL_OR_PASSWORD);
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
