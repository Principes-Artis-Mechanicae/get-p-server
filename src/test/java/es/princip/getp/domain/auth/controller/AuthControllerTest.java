package es.princip.getp.domain.auth.controller;

import es.princip.getp.domain.auth.dto.request.LoginRequest;
import es.princip.getp.domain.auth.dto.response.Token;
import es.princip.getp.domain.auth.exception.LoginErrorCode;
import es.princip.getp.domain.auth.fixture.LoginFixture;
import es.princip.getp.domain.auth.service.AuthService;
import es.princip.getp.global.controller.ErrorCodeController;
import es.princip.getp.global.exception.BusinessLogicException;
import es.princip.getp.global.mock.WithCustomMockUser;
import es.princip.getp.global.support.AbstractControllerTest;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static es.princip.getp.global.support.ErrorCodeFields.errorCodeFields;
import static es.princip.getp.global.support.FieldDescriptorHelper.getDescriptor;
import static es.princip.getp.global.support.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.global.support.PayloadDocumentationHelper.responseFields;
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

        @Test
        void loginErrorCode() throws Exception {
            mockMvc.perform(get("/error-code/login"))
                .andDo(restDocs.document(errorCodeFields(LoginErrorCode.values())));
        }

        @DisplayName("로그인을 할 수 있다.")
        @Test
        void login() throws Exception {
            given(authService.login(LoginFixture.createLoginRequest()))
                .willReturn(Token.builder()
                    .grantType("Bearer")
                    .accessToken("access-token")
                    .refreshToken("refresh-token")
                    .build());

            mockMvc.perform(post("/auth/login")
                .content(objectMapper.writeValueAsString(LoginFixture.createLoginRequest()))
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
            given(authService.login(LoginFixture.createLoginRequest()))
                .willThrow(new BusinessLogicException(LoginErrorCode.INCORRECT_EMAIL_OR_PASSWORD));

            mockMvc.perform(post("/auth/login")
                .content(objectMapper.writeValueAsString(LoginFixture.createLoginRequest()))
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
        @WithCustomMockUser
        void reissueAccessToken() throws Exception {
            given(authService.reissueAccessToken(any(HttpServletRequest.class)))
                .willReturn(Token.builder()
                    .grantType("Bearer")
                    .accessToken("access-token")
                    .refreshToken("refresh-token")
                    .build());

            mockMvc.perform(post("/auth/reissue")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
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