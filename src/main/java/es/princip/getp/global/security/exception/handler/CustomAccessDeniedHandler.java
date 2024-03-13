package es.princip.getp.global.security.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.princip.getp.global.security.exception.SecurityErrorCode;
import es.princip.getp.global.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

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
