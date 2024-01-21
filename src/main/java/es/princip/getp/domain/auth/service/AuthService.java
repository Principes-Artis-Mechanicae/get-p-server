package es.princip.getp.domain.auth.service;

import es.princip.getp.domain.auth.entity.TokenVerification;
import es.princip.getp.domain.auth.exception.EmailErrorCode;
import es.princip.getp.domain.auth.exception.SignUpErrorCode;
import es.princip.getp.domain.auth.exception.TokenErrorCode;
import es.princip.getp.domain.auth.repository.TokenVerificationRepository;
import es.princip.getp.domain.member.service.MemberService;
import es.princip.getp.global.exception.BusinessLogicException;
import es.princip.getp.global.security.details.PrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.princip.getp.domain.auth.dto.request.LoginRequest;
import es.princip.getp.domain.auth.dto.response.Token;
import es.princip.getp.global.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final MemberService memberService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final TokenVerificationRepository tokenVerificationRepository;
    
    public Token login(LoginRequest loginRequest) {
        String email = loginRequest.email();
        String password = loginRequest.password();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder
            .getObject()
            .authenticate(authenticationToken);

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Long memberId = principalDetails.getMember().getMemberId();

        Token token = jwtTokenProvider.generateToken(authentication);
        cacheToken(memberId, token);

        return token;
    }

    public Token reissueAccessToken(HttpServletRequest servletRequest) {
        String refreshToken = jwtTokenProvider.resolveToken(servletRequest);
        if (isValidRefreshToken(refreshToken)) {
            Authentication authentication =
                    jwtTokenProvider.getAuthentication(refreshToken, memberService);

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            Long memberId = principalDetails.getMember().getMemberId();

            Token token = jwtTokenProvider.generateToken(authentication);
            cacheToken(memberId, token);

            return token;
        } else {
            throw new BusinessLogicException(TokenErrorCode.EXPIRED_REFRESH_TOKEN);
        }
    }

    private boolean isValidRefreshToken(String refreshToken) {
        return refreshToken != null
                && jwtTokenProvider.validateToken(refreshToken)
                && tokenVerificationRepository.existsByRefreshToken(refreshToken);
    }

    private void cacheToken(Long memberId, Token token) {
        String refreshToken = token.refreshToken();
        tokenVerificationRepository.save(new TokenVerification(memberId, refreshToken));
    }
}
