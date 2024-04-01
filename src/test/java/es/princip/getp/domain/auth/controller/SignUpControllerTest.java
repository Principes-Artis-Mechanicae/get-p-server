package es.princip.getp.domain.auth.controller;

import static es.princip.getp.fixture.SignUpFixture.createSignUpRequest;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.auth.service.SignUpService;
import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.member.domain.enums.MemberType;
import es.princip.getp.global.config.SecurityConfig;
import es.princip.getp.global.config.SecurityTestConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SignUpController.class)
@Import({SecurityConfig.class, SecurityTestConfig.class})
@ActiveProfiles("test")
@RequiredArgsConstructor
class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SignUpService signUpService;

    @Nested
    @DisplayName("사용자는")
    class SignUp {

        @DisplayName("회원 가입을 할 수 있다.")
        @ParameterizedTest
        @EnumSource(value = MemberType.class, names = { "ROLE_PEOPLE", "ROLE_CLIENT" })
        void signUp(MemberType memberType) throws Exception {
            SignUpRequest signUpRequest = createSignUpRequest(memberType);
            given(signUpService.signUp(signUpRequest)).willReturn(
                Member.builder()
                    .email(signUpRequest.email())
                    .memberType(signUpRequest.memberType())
                    .build()
            );

            mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated());
        }

        @DisplayName("관리자 또는 매니저로 회원 가입할 수 없다.")
        @ParameterizedTest
        @EnumSource(value = MemberType.class, names = { "ROLE_ADMIN", "ROLE_MANAGER" })
        void signUp_WhenMemberTypeIsNotPeopleOrClient_ShouldFail(MemberType memberType)
            throws Exception {
            SignUpRequest signUpRequest = createSignUpRequest(memberType);

            mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest());
        }
    }
}