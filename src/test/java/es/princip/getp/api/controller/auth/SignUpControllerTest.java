package es.princip.getp.api.controller.auth;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.controller.auth.dto.request.EmailVerificationCodeRequest;
import es.princip.getp.api.controller.auth.dto.request.ServiceTermAgreementRequest;
import es.princip.getp.api.controller.auth.dto.request.SignUpRequest;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.auth.command.SignUpCommand;
import es.princip.getp.application.auth.service.SignUpService;
import es.princip.getp.domain.member.model.MemberType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.docs.ConstraintDescriptor.fieldWithConstraint;
import static es.princip.getp.api.docs.EnumDescriptor.fieldWithEnum;
import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static es.princip.getp.fixture.auth.EmailVerificationFixture.VERIFICATION_CODE;
import static es.princip.getp.fixture.common.EmailFixture.EMAIL;
import static es.princip.getp.fixture.member.PasswordFixture.PASSWORD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SignUpControllerTest extends ControllerTest {

    @Autowired private SignUpService signUpService;

    @Nested
    class 이메일_인증_코드_전송 {

        private final EmailVerificationCodeRequest request = new EmailVerificationCodeRequest(EMAIL);

        @Test
        void 사용자는_회원_가입_시_이메일_인증을_해야_한다() throws Exception {
            mockMvc.perform(post("/auth/signup/email/send")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("auth/send-email-verification-code-for-signup",
                    ResourceSnippetParameters.builder()
                        .tag("인증")
                        .description("사용자는 회원 가입 시 이메일 인증을 해야 한다.")
                        .summary("이메일 인증 코드 전송")
                        .requestSchema(Schema.schema("EmailVerificationCodeRequest"))
                        .responseSchema(Schema.schema("StatusResponse")),
                    requestFields(
                        fieldWithConstraint("email", EmailVerificationCodeRequest.class)
                            .description("이메일")
                    ),
                    responseFields(statusField())
                ))
                .andDo(print());
        }
    }

    @Nested
    class 회원_가입 {

        @ParameterizedTest
        @EnumSource(value = MemberType.class, names = { "ROLE_PEOPLE", "ROLE_CLIENT" })
        void 사용자는_회원_가입을_할_수_있다(MemberType memberType) throws Exception {
            final SignUpRequest request = new SignUpRequest(
                EMAIL,
                PASSWORD,
                VERIFICATION_CODE,
                Set.of(
                    new ServiceTermAgreementRequest("tag1", true),
                    new ServiceTermAgreementRequest("tag2", false)
                ),
                memberType
            );
            willDoNothing().given(signUpService).signUp(any(SignUpCommand.class));

            mockMvc.perform(post("/auth/signup")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(document("auth/signup",
                    ResourceSnippetParameters.builder()
                        .tag("인증")
                        .description("사용자는 회원 가입을 할 수 있다.")
                        .summary("회원 가입")
                        .requestSchema(Schema.schema("SignUpRequest"))
                        .responseSchema(Schema.schema("StatusResponse")),
                    requestFields(
                        fieldWithConstraint("email", SignUpRequest.class)
                            .description("이메일"),
                        fieldWithConstraint("password", SignUpRequest.class)
                            .description("비밀번호"),
                        fieldWithEnum(MemberType.class).withPath("memberType")
                            .description("회원 유형. 일반 사용자는 피플인 ROLE_PEOPLE 또는 의뢰자인 ROLE_CLIENT만 가능"),
                        fieldWithConstraint("verificationCode", SignUpRequest.class)
                            .description("이메일 인증 코드"),
                        fieldWithConstraint("serviceTerms[].tag", ServiceTermAgreementRequest.class)
                            .description("서비스 약관 태그"),
                        fieldWithConstraint("serviceTerms[].agreed", ServiceTermAgreementRequest.class)
                            .description("서비스 약관 동의 여부")
                    ),
                    responseFields(statusField())
                ))
                .andDo(print());
        }
    }
}