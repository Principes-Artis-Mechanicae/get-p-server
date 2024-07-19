package es.princip.getp.domain.auth.presentation;

import es.princip.getp.domain.auth.application.AuthService;
import es.princip.getp.domain.auth.exception.LoginErrorCode;
import es.princip.getp.domain.auth.presentation.dto.request.LoginRequest;
import es.princip.getp.domain.auth.presentation.dto.response.Token;
import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.presentation.ErrorCodeController;
import es.princip.getp.infra.support.AbstractControllerTest;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static es.princip.getp.domain.member.fixture.EmailFixture.EMAIL;
import static es.princip.getp.domain.member.fixture.PasswordFixture.PASSWORD;
import static es.princip.getp.infra.util.ErrorCodeFields.errorCodeFields;
import static es.princip.getp.infra.util.FieldDescriptorHelper.getDescriptor;
import static es.princip.getp.infra.util.HeaderDescriptorHelper.refreshTokenHeaderDescriptor;
import static es.princip.getp.infra.util.PayloadDocumentationHelper.responseFields;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({AuthController.class, ErrorCodeController.class})
class AuthControllerTest extends AbstractControllerTest {

    @MockBean
    private AuthService authService;

    @DisplayName("사용자는")
    @Nested
    class Login {

        final LoginRequest request = new LoginRequest(EMAIL, PASSWORD);

        @Test
        void loginErrorCode() throws Exception {
            mockMvc.perform(get("/error-code/login"))
                .andDo(restDocs.document(errorCodeFields(LoginErrorCode.values())));
        }

        @DisplayName("로그인을 할 수 있다.")
        @Test
        void login() throws Exception {
            final Token token = new Token("Bearer", "${ACCESS_TOKEN}", "${REFRESH_TOKEN}");
            given(authService.login(request)).willReturn(token);

            mockMvc.perform(post("/auth/login")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
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

        @DisplayName("올바르지 않은 이메일 또는 비밀번호인 경우 로그인할 수 없다.")
        @Test
        void login_WhenEmailAndPasswordIsIncorrect_ShouldFail() throws Exception {
            given(authService.login(request))
                .willThrow(new BusinessLogicException(LoginErrorCode.INCORRECT_EMAIL_OR_PASSWORD));

            mockMvc.perform(post("/auth/login")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(errorCode(LoginErrorCode.INCORRECT_EMAIL_OR_PASSWORD))
                .andDo(print());
        }
    }

    @DisplayName("사용자는")
    @Nested
    class ReissueAccessToken {

        @DisplayName("로그인 유지를 할 수 있다.")
        @Test
        void reissueAccessToken() throws Exception {
            final Token token = new Token("Bearer", "${ACCESS_TOKEN}", "${REFRESH_TOKEN}");
            given(authService.reissueAccessToken(any(HttpServletRequest.class)))
                .willReturn(token);

            mockMvc.perform(post("/auth/reissue")
                .header("Refresh-Token", "Bearer ${REFRESH_TOKEN}")
                .contentType(MediaType.APPLICATION_JSON))
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