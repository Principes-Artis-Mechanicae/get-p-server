package es.princip.getp.api.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.princip.getp.application.auth.exception.JwtTokenException;
import es.princip.getp.api.support.dto.ApiErrorResponse;
import es.princip.getp.api.support.dto.ApiErrorResponse.ApiErrorResult;
import es.princip.getp.domain.support.ErrorDescription;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class AccessTokenExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper;

    /**
     * Access Token 관련 예외를 필터링한다. Access Token이 유효하지 않거나 만료되었을 경우 에러를 클라이언트에게 반환한다.
     */
    @Override
    protected void doFilterInternal(
        @NonNull final HttpServletRequest request,
        @NonNull final HttpServletResponse response,
        @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtTokenException exception) {
            final ErrorDescription description = exception.getDescription();
            final ApiErrorResult result = ApiErrorResponse.error(HttpStatus.UNAUTHORIZED, description).getBody();
            final String body = mapper.writeValueAsString(result);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write(body);
        }
    }
}
