package es.princip.getp.global.security.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.princip.getp.domain.auth.exception.TokenErrorCode;
import es.princip.getp.global.util.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
        String body = mapper.writeValueAsString(ApiResponse.error(TokenErrorCode.INVALID_ACCESS_TOKEN));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(TokenErrorCode.INVALID_ACCESS_TOKEN.status().value());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(body);
    }
}
