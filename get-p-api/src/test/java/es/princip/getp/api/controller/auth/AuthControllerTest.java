package es.princip.getp.api.controller.auth;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.controller.auth.dto.request.LoginRequest;
import es.princip.getp.application.auth.dto.response.Token;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.docs.ConstraintDescriptor.fieldWithConstraint;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.refreshTokenHeaderDescription;
import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static es.princip.getp.fixture.common.EmailFixture.EMAIL;
import static es.princip.getp.fixture.member.PasswordFixture.PASSWORD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends ControllerTest {

    @Autowired
    private AuthService authService;

    @Nested
    class 로그인 {

        private final LoginRequest request = new LoginRequest(EMAIL, PASSWORD);

        @Test
        void 사용자는_로그인을_할_수_있다() throws Exception {
            final Token token = new Token("Bearer", "${ACCESS_TOKEN}", "${REFRESH_TOKEN}");
            given(authService.login(request)).willReturn(token);

            mockMvc.perform(post("/auth/login")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(document(
                    "auth/login",
                    ResourceSnippetParameters.builder()
                        .tag("인증")
                        .description("사용자는 로그인을 할 수 있다.")
                        .summary("로그인")
                        .requestSchema(Schema.schema("LoginRequest"))
                        .responseSchema(Schema.schema("TokenResponse")),
                    requestFields(
                        fieldWithConstraint("email", LoginRequest.class).description("이메일"),
                        fieldWithConstraint("password", LoginRequest.class).description("비밀번호")
                    ),
                    responseFields(
                        statusField(),
                        fieldWithPath("data.grantType").description("토큰 타입")
                            .attributes(key("format").value("Bearer")),
                        fieldWithPath("data.accessToken").description("Access Token"),
                        fieldWithPath("data.refreshToken").description("Refresh Token")
                    )
                ))
                .andDo(print());
        }
    }

    @Nested
    class Access_Token_재발급 {

        @Test
        void 사용자는_로그인_유지를_할_수있다() throws Exception {
            final Token token = new Token(
            "Bearer",
            "${ACCESS_TOKEN}",
            "${REFRESH_TOKEN}"
            );
            given(authService.reissueAccessToken(any(HttpServletRequest.class)))
                .willReturn(token);

            mockMvc.perform(post("/auth/reissue")
                .header("Refresh-Token", "Bearer ${REFRESH_TOKEN}"))
                .andExpect(status().isCreated())
                .andDo(document(
                    "auth/reissue-access-token",
                    ResourceSnippetParameters.builder()
                        .tag("인증")
                        .description("사용자는 로그인 유지를 할 수 있다.")
                        .summary("Access Token 재발급")
                        .requestSchema(Schema.schema("ReissueAccessTokenRequest"))
                        .responseSchema(Schema.schema("TokenResponse")),
                    requestHeaders(refreshTokenHeaderDescription()),
                    responseFields(
                        statusField(),
                        fieldWithPath("data.grantType").description("토큰 타입")
                            .attributes(key("format").value("Bearer")),
                        fieldWithPath("data.accessToken").description("Access Token"),
                        fieldWithPath("data.refreshToken").description("Refresh Token")
                    )
                ))
                .andDo(print());
        }
    }
}