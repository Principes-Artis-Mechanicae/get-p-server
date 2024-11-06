package es.princip.getp.api.security.filter;

import es.princip.getp.application.auth.exception.ExpiredTokenException;
import es.princip.getp.application.auth.exception.InvalidTokenException;
import es.princip.getp.application.auth.service.AccessTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AccessTokenAuthorizationFilter extends OncePerRequestFilter {

    private final AccessTokenService accessTokenService;

    /**
     * Access Token을 검증한 뒤, 유효한 경우 SecurityContext에 인증 정보를 저장한다.
     * Access Token이 없거나 OPTIONS 요청일 경우 필터를 통과한다.
     *
     * @throws ExpiredTokenException Access Token이 만료된 경우
     * @throws InvalidTokenException Access Token이 유효하지 않은 경우
     */
    @Override
    protected void doFilterInternal(
        @NonNull final HttpServletRequest request,
        @NonNull final HttpServletResponse response,
        @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {
        if (isOptionsRequest(request)) {
            filterChain.doFilter(request, response);
            return ;
        }
        final String accessToken = accessTokenService.resolveToken(request);
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return ;
        }
        final Authentication authentication = accessTokenService.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private boolean isOptionsRequest(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }
}
