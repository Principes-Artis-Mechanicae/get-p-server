package es.princip.getp.domain.auth.service;

import es.princip.getp.domain.auth.domain.entity.TokenVerification;
import es.princip.getp.domain.auth.dto.request.LoginRequest;
import es.princip.getp.domain.auth.dto.response.Token;
import es.princip.getp.domain.auth.exception.LoginErrorCode;
import es.princip.getp.domain.auth.exception.TokenErrorCode;
import es.princip.getp.domain.auth.repository.TokenVerificationRepository;
import es.princip.getp.domain.member.service.MemberService;
import es.princip.getp.global.exception.BusinessLogicException;
import es.princip.getp.global.security.details.PrincipalDetails;
import es.princip.getp.global.security.provider.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final TokenVerificationRepository tokenVerificationRepository;

    @Value("${spring.jwt.refresh-token.expire-time}")
    private Long REFRESH_TOKEN_EXPIRE_TIME;
    
    public Token login(LoginRequest request) {
        String email = request.email();
        String password = request.password();
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
            Authentication authentication = authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            Long memberId = principalDetails.getMember().getMemberId();
            Token token = jwtTokenProvider.generateToken(authentication);
            cacheRefreshToken(memberId, token.refreshToken());
            return token;
        } catch (Exception e) {
            throw new BusinessLogicException(LoginErrorCode.INCORRECT_EMAIL_OR_PASSWORD);
        }
    }

    public Token reissueAccessToken(HttpServletRequest servletRequest) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(servletRequest);
        if (isValidRefreshToken(refreshToken)) {
            Authentication authentication =
                    jwtTokenProvider.getAuthentication(refreshToken, memberService);
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            Long memberId = principalDetails.getMember().getMemberId();
            Token token = jwtTokenProvider.generateToken(authentication);
            cacheRefreshToken(memberId, token.refreshToken());
            return token;
        } else {
            throw new BusinessLogicException(TokenErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    private boolean isValidRefreshToken(String refreshToken) {
        return refreshToken != null
                && jwtTokenProvider.validateToken(refreshToken)
                && tokenVerificationRepository.existsByRefreshToken(refreshToken);
    }

    private void cacheRefreshToken(Long memberId, String refreshToken) {
        tokenVerificationRepository.save(new TokenVerification(memberId, refreshToken, REFRESH_TOKEN_EXPIRE_TIME));
    }
}
