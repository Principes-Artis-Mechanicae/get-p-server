package es.princip.getp.api.controller.auth;

import es.princip.getp.api.controller.ControllerTest;
import es.princip.getp.api.controller.auth.dto.request.LoginRequest;
import es.princip.getp.api.controller.auth.dto.response.Token;
import es.princip.getp.application.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.refreshTokenHeaderDescriptor;
import static es.princip.getp.api.docs.PayloadDocumentationHelper.responseFields;
import static es.princip.getp.domain.member.fixture.EmailFixture.EMAIL;
import static es.princip.getp.domain.member.fixture.PasswordFixture.PASSWORD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends ControllerTest {

    @Autowired
    private AuthService authService;

    @Nested
    class Login {

        final LoginRequest request = new LoginRequest(EMAIL, PASSWORD);

        @DisplayName("사용자는 로그인을 할 수 있다.")
        @Test
        void login() throws Exception {
            final Token token = new Token("Bearer", "${ACCESS_TOKEN}", "${REFRESH_TOKEN}");
            given(authService.login(request)).willReturn(token);

            mockMvc.perform(post("/auth/login")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(
                    restDocs.document(
                        requestFields(
                            getDescriptor("email", "이메일", LoginRequest.class),
                            getDescriptor("password", "비밀번호", LoginRequest.class)
                        ),
                        responseFields(
                            getDescriptor("grantType", "토큰 타입", Token.class)
                                .attributes(key("format").value("Bearer")),
                            getDescriptor("accessToken", "Access Token", Token.class),
                            getDescriptor("refreshToken", "Refresh Token", Token.class)
                        )
                    )
                )
                .andDo(print());
        }
    }

    @Nested
    class ReissueAccessToken {

        @DisplayName("사용자는 로그인 유지를 할 수 있다.")
        @Test
        void reissueAccessToken() throws Exception {
            final Token token = new Token("Bearer", "${ACCESS_TOKEN}", "${REFRESH_TOKEN}");
            given(authService.reissueAccessToken(any(HttpServletRequest.class)))
                .willReturn(token);

            mockMvc.perform(post("/auth/reissue")
                .header("Refresh-Token", "Bearer ${REFRESH_TOKEN}"))
                .andExpect(status().isCreated())
                .andDo(
                    restDocs.document(
                        requestHeaders(refreshTokenHeaderDescriptor()),
                        responseFields(
                            getDescriptor("grantType", "토큰 타입", Token.class)
                                .attributes(key("format").value("Bearer")),
                            getDescriptor("accessToken", "Access Token", Token.class),
                            getDescriptor("refreshToken", "Refresh Token", Token.class)
                        )
                    )
                )
                .andDo(print());
        }
    }
}