package es.princip.getp.infra.security.filter;

import es.princip.getp.infra.security.provider.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        if (isOptionsRequest(servletRequest) || isReissueRequest(servletRequest)) {
            chain.doFilter(request, response);
            return ;
        }
        String token = jwtTokenProvider.resolveAccessToken(servletRequest);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
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
