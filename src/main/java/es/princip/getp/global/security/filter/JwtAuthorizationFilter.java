package es.princip.getp.global.security.filter;

import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import es.princip.getp.domain.member.service.MemberService;
import es.princip.getp.global.security.provider.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends GenericFilterBean {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;

        if (isOptionsRequest(servletRequest) || isReissueRequest(servletRequest)) {
            chain.doFilter(request, response);
            return;
        }

        String token = jwtTokenProvider.resolveAccessToken(servletRequest);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication
                    = jwtTokenProvider.getAuthentication(token, memberService);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    private boolean isOptionsRequest(HttpServletRequest request) {
        return request.getMethod().equals("OPTIONS");
    }

    private boolean isReissueRequest(HttpServletRequest request) {
        return request.getServletPath().equals("/api/v1/auth/reissue");
    }
}
