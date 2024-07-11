package es.princip.getp.domain.auth.service;

import static es.princip.getp.domain.auth.fixture.SignUpFixture.createSignUpRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.auth.dto.response.SignUpResponse;
import es.princip.getp.domain.auth.exception.SignUpErrorCode;
import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.member.domain.MemberType;
import es.princip.getp.domain.member.dto.request.CreateMemberRequest;
import es.princip.getp.domain.member.service.MemberService;
import es.princip.getp.infra.exception.BusinessLogicException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class SignUpServiceTest {

    @Mock
    private EmailVerificationService emailVerificationService;

    @Mock
    private MemberService memberService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SignUpService signUpService;

    @Nested
    @DisplayName("sendEmailVerificationCodeForSignUp()은")
    class SendEmailVerificationCodeForSignUp {

        @Test
        @DisplayName("회원 가입을 위한 이메일 인증 코드를 전송한다.")
        void sendEmailVerificationCodeForSignUp() {
            String email = "test@example.com";
            given(memberService.existsByEmail(email)).willReturn(false);

            signUpService.sendEmailVerificationCodeForSignUp(email);

            verify(emailVerificationService, times(1)).sendEmailVerificationCode(email);
        }

        @Test
        @DisplayName("이미 가입된 이메일인 경우 실패한다.")
        void sendEmailVerificationCodeForSignUp_WhenEmailIsDuplicated_ShouldThrowException() {
            String email = "test@example.com";
            given(memberService.existsByEmail(email)).willReturn(true);

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class,
                    () -> signUpService.sendEmailVerificationCodeForSignUp(email));
            assertThat(exception.getErrorCode()).isEqualTo(SignUpErrorCode.DUPLICATED_EMAIL);
        }
    }

    @Nested
    @DisplayName("signUp()은")
    class SignUp {

        @DisplayName("회원 가입을 진행한다.")
        @Test
        void signUp() {
            SignUpRequest signUpRequest = createSignUpRequest(MemberType.ROLE_PEOPLE);
            CreateMemberRequest createMemberRequest = CreateMemberRequest.from(
                signUpRequest,
                passwordEncoder);
            Member member = Member.from(createMemberRequest);
            given(memberService.create(createMemberRequest)).willReturn(member);

            SignUpResponse response = signUpService.signUp(signUpRequest);

            assertThat(response.email()).isEqualTo(signUpRequest.email());
            assertThat(response.memberType()).isEqualTo(signUpRequest.memberType());
            verify(emailVerificationService, times(1))
                .verifyEmail(signUpRequest.email(), signUpRequest.verificationCode());
        }

        @DisplayName("이메일이 인증되지 않은 경우 실패한다.")
        @Test
        void signUp_WhenEmailIsNotVerified_ShouldThrowException() {
            SignUpRequest signUpRequest = createSignUpRequest(MemberType.ROLE_PEOPLE);
            doThrow(new BusinessLogicException(SignUpErrorCode.NOT_VERIFIED_EMAIL))
                .when(emailVerificationService)
                .verifyEmail(signUpRequest.email(), signUpRequest.verificationCode());

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class,
                    () -> signUpService.signUp(signUpRequest));

            assertThat(exception.getErrorCode()).isEqualTo(SignUpErrorCode.NOT_VERIFIED_EMAIL);
        }
    }
}