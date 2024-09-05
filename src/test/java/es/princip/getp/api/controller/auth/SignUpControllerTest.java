package es.princip.getp.api.controller.auth;

import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.api.controller.auth.dto.request.EmailVerificationCodeRequest;
import es.princip.getp.api.controller.auth.dto.request.ServiceTermAgreementRequest;
import es.princip.getp.api.controller.auth.dto.request.SignUpRequest;
import es.princip.getp.application.auth.command.SignUpCommand;
import es.princip.getp.application.auth.service.SignUpService;
import es.princip.getp.domain.member.model.MemberType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;
import static es.princip.getp.fixture.auth.EmailVerificationFixture.VERIFICATION_CODE;
import static es.princip.getp.fixture.common.EmailFixture.EMAIL;
import static es.princip.getp.fixture.member.PasswordFixture.PASSWORD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SignUpControllerTest extends ControllerTest {

    @Autowired
    private SignUpCommandMapper mapper;

    @Autowired
    private SignUpService signUpService;

    @Nested
    class SendEmailVerificationCodeForSignUp {

        private final EmailVerificationCodeRequest request = new EmailVerificationCodeRequest(EMAIL);

        @DisplayName("사용자는 회원 가입 시 이메일 인증을 해야 한다.")
        @Test
        void sendEmailVerificationCodeForSignUp() throws Exception {
            mockMvc.perform(post("/auth/signup/email/send")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        requestFields(
                            getDescriptor("email", "이메일", EmailVerificationCodeRequest.class)
                        )
                    )
                )
                .andDo(print());
        }
    }

    @Nested
    class SignUp {

        @DisplayName("사용자는 회원 가입을 할 수 있다.")
        @ParameterizedTest
        @EnumSource(value = MemberType.class, names = { "ROLE_PEOPLE", "ROLE_CLIENT" })
        void signUp(MemberType memberType) throws Exception {
            SignUpRequest request = new SignUpRequest(
                EMAIL,
                PASSWORD,
                VERIFICATION_CODE,
                Set.of(
                    new ServiceTermAgreementRequest("tag1", true),
                    new ServiceTermAgreementRequest("tag2", false)
                ),
                memberType
            );
            given(mapper.mapToCommand(request)).willReturn(mock(SignUpCommand.class));
            willDoNothing().given(signUpService).signUp(any(SignUpCommand.class));

            mockMvc.perform(post("/auth/signup")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(
                    restDocs.document(
                        requestFields(
                            getDescriptor("email", "이메일", SignUpRequest.class),
                            getDescriptor("password", "비밀번호", SignUpRequest.class),
                            getDescriptor("memberType", "회원 유형", SignUpRequest.class)
                                .attributes(key("format").value("ROLE_PEOPLE, ROLE_CLIENT")),
                            getDescriptor("verificationCode", "이메일 인증 코드", SignUpRequest.class),
                            getDescriptor("serviceTerms[].tag", "서비스 약관 태그", ServiceTermAgreementRequest.class),
                            getDescriptor("serviceTerms[].agreed", "서비스 약관 동의 여부", ServiceTermAgreementRequest.class)
                        )
                    )
                )
                .andDo(print());
        }

        @DisplayName("관리자 또는 매니저로 회원 가입할 수 없다.")
        @ParameterizedTest
        @EnumSource(value = MemberType.class, names = { "ROLE_ADMIN", "ROLE_MANAGER" })
        void signUp_WhenMemberTypeIsNotPeopleOrClient_ShouldFail(MemberType memberType)
            throws Exception {
            SignUpRequest request = new SignUpRequest(
                EMAIL,
                PASSWORD,
                VERIFICATION_CODE,
                Set.of(
                    new ServiceTermAgreementRequest("tag1", true),
                    new ServiceTermAgreementRequest("tag2", false)
                ),
                memberType
            );

            mockMvc.perform(post("/auth/signup")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print());
        }
    }
}