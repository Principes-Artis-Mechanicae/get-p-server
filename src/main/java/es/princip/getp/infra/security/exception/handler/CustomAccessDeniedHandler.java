package es.princip.getp.infra.security.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.princip.getp.infra.dto.response.ApiResponse;
import es.princip.getp.infra.security.exception.SecurityErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException {
        String body = mapper.writeValueAsString(ApiResponse.error(
            SecurityErrorCode.FORBIDDEN));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(SecurityErrorCode.FORBIDDEN.status().value());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(body);
    }
}
