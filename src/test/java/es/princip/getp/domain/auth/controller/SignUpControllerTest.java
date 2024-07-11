package es.princip.getp.domain.auth.controller;

import es.princip.getp.domain.auth.dto.request.EmailVerificationCodeRequest;
import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.auth.exception.SignUpErrorCode;
import es.princip.getp.domain.auth.service.SignUpService;
import es.princip.getp.domain.auth.support.SignUpFixture;
import es.princip.getp.domain.member.domain.MemberType;
import es.princip.getp.domain.serviceTerm.dto.reqeust.ServiceTermAgreementRequest;
import es.princip.getp.infra.controller.ErrorCodeController;
import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static es.princip.getp.domain.auth.support.SignUpFixture.createSignUpRequest;
import static es.princip.getp.infra.support.ErrorCodeFields.errorCodeFields;
import static es.princip.getp.infra.support.FieldDescriptorHelper.getDescriptor;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({SignUpController.class, ErrorCodeController.class})
public class SignUpControllerTest extends AbstractControllerTest {

    @MockBean
    private SignUpService signUpService;

    @Nested
    @DisplayName("사용자는")
    class SendEmailVerificationCodeForSignUp {

        @DisplayName("회원 가입 시 이메일 인증을 해야 한다.")
        @Test
        void sendEmailVerificationCodeForSignUp() throws Exception {
            mockMvc.perform(post("/auth/signup/email/send")
            .content(objectMapper.writeValueAsString(SignUpFixture.createEmailVerificationCodeRequest())))
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
    @DisplayName("사용자는")
    class SignUp {

        @Test
        void signUpErrorCode() throws Exception {
            mockMvc.perform(get("/error-code/signup"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(errorCodeFields(SignUpErrorCode.values())));
        }

        @DisplayName("회원 가입을 할 수 있다.")
        @ParameterizedTest
        @EnumSource(value = MemberType.class, names = { "ROLE_PEOPLE", "ROLE_CLIENT" })
        void signUp(MemberType memberType) throws Exception {
            SignUpRequest request = createSignUpRequest(memberType);
            given(signUpService.signUp(request)).willReturn(SignUpFixture.toSignUpResponse(request));

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
                        ),
                        responseFields(beneathPath("data").withSubsectionId("data"))
                        .and(
                            getDescriptor("email", "이메일"),
                            getDescriptor("memberType", "회원 유형"),
                            getDescriptor("serviceTerms[].tag", "서비스 약관 태그"),
                            getDescriptor("serviceTerms[].required", "서비스 약관 필수 동의 여부"),
                            getDescriptor("serviceTerms[].agreed", "서비스 약관 동의 여부"),
                            getDescriptor("serviceTerms[].revocable", "서비스 약관 동의 거절 가능 여부"),
                            getDescriptor("serviceTerms[].agreedAt", "서비스 약관 동의 시각")
                                .attributes(key("format").value("yyyy-MM-dd'T'HH:mm:ss"))
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
            SignUpRequest request = createSignUpRequest(memberType);

            mockMvc.perform(post("/auth/signup")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        }

        @DisplayName("이미 가입된 이메일 주소로 회원 가입할 수 없다.")
        @Test
        void signUp_WhenEmailIsDuplicated_ShouldFail() throws Exception {
            SignUpRequest request = createSignUpRequest(MemberType.ROLE_PEOPLE);
            doThrow(new BusinessLogicException(SignUpErrorCode.DUPLICATED_EMAIL))
                .when(signUpService).signUp(request);

            mockMvc.perform(post("/auth/signup")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(errorCode(SignUpErrorCode.DUPLICATED_EMAIL));
        }

        @DisplayName("이메일을 인증하지 않으면 회원 가입할 수 없다.")
        @Test
        void signUp_WhenEmailIsNotVerified_ShouldFail() throws Exception {
            SignUpRequest request = createSignUpRequest(MemberType.ROLE_PEOPLE);
            doThrow(new BusinessLogicException(SignUpErrorCode.NOT_VERIFIED_EMAIL))
                .when(signUpService).signUp(request);

            mockMvc.perform(post("/auth/signup")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(errorCode(SignUpErrorCode.NOT_VERIFIED_EMAIL));
        }

        @DisplayName("필수 서비스 약관에 동의하지 않으면 회원 가입할 수 없다.")
        @Test
        void signUp_WhenNotAgreedRequiredServiceTerm_ShouldFail() throws Exception {
            SignUpRequest request = createSignUpRequest(MemberType.ROLE_PEOPLE);
            doThrow(new BusinessLogicException(SignUpErrorCode.NOT_AGREED_REQUIRED_SERVICE_TERM))
                .when(signUpService).signUp(request);

            mockMvc.perform(post("/auth/signup")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(errorCode(SignUpErrorCode.NOT_AGREED_REQUIRED_SERVICE_TERM));
        }
    }
}