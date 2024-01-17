package es.princip.getp.domain.auth.service;

import static es.princip.getp.domain.auth.fixture.SignUpFixture.createSignUpRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.auth.exception.SignUpErrorCode;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.service.MemberService;
import es.princip.getp.domain.serviceTermAgreement.service.ServiceTermAgreementService;
import es.princip.getp.global.exception.BusinessLogicException;
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
    private ServiceTermAgreementService serviceTermAgreementService;

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
            when(memberService.existsByEmail(email)).thenReturn(false);

            signUpService.sendEmailVerificationCodeForSignUp(email);

            verify(emailVerificationService, times(1)).sendEmailVerificationCode(email);
        }

        @Test
        @DisplayName("이메일이 중복되는 경우 실패한다.")
        void sendEmailVerificationCodeForSignUp_WhenEmailIsDuplicated_ShouldThrowException() {
            String email = "test@example.com";
            when(memberService.existsByEmail(email)).thenReturn(true);

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class,
                    () -> signUpService.sendEmailVerificationCodeForSignUp(email));
            assertEquals(exception.getCode(), SignUpErrorCode.DUPLICATED_EMAIL.name());
        }
    }

    @Nested
    @DisplayName("signUp()은")
    class SignUp {
        @DisplayName("회원 가입을 진행한다.")
        @Test
        void signUp() {
            SignUpRequest signUpRequest = createSignUpRequest("test@example.com", "qwer1234!");
            Member member = signUpRequest.toEntity(passwordEncoder);
            when(memberService.existsByEmail(signUpRequest.email())).thenReturn(false);
            when(emailVerificationService.isVerifiedEmail(signUpRequest.email())).thenReturn(true);
            when(serviceTermAgreementService.isAgreedAllRequiredServiceTerms(
                signUpRequest.serviceTerms())).thenReturn(true);
            when(memberService.create(any())).thenReturn(member);

            Member signedUpMember = signUpService.signUp(signUpRequest);

            assertEquals(signedUpMember, member);
            verify(serviceTermAgreementService, times(1)).agree(signedUpMember.getMemberId(),
                signUpRequest.serviceTerms());
        }

        @DisplayName("이메일이 중복되는 경우 실패한다.")
        @Test
        void signUp_WhenEmailIsDuplicated_ShouldThrowException() {
            SignUpRequest signUpRequest = createSignUpRequest("test@example.com", "qwer1234!");
            when(memberService.existsByEmail(signUpRequest.email())).thenReturn(true);

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class,
                    () -> signUpService.signUp(signUpRequest));

            assertEquals(exception.getCode(), SignUpErrorCode.DUPLICATED_EMAIL.name());
        }

        @DisplayName("이메일 형식이 잘못된 경우 실패한다.")
        @Test
        void signUp_WhenEmailIsWrong_ShouldThrowException() {
            SignUpRequest signUpRequest = createSignUpRequest("testexample.com", "qwer1234!");

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class,
                    () -> signUpService.signUp(signUpRequest));

            assertEquals(exception.getCode(), SignUpErrorCode.WRONG_EMAIL.name());
        }

        @DisplayName("비밀번호 형식이 잘못된 경우 실패한다.")
        @Test
        void signUp_WhenPasswordIsWrong_ShouldThrowException() {
            SignUpRequest signUpRequest = createSignUpRequest("test@example.com", "qwer1234");

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class,
                    () -> signUpService.signUp(signUpRequest));

            assertEquals(exception.getCode(), SignUpErrorCode.WRONG_PASSWORD.name());
        }

        @DisplayName("이메일이 인증되지 않은 경우 실패한다.")
        @Test
        void signUp_WhenEmailIsNotVerified_ShouldThrowException() {
            SignUpRequest signUpRequest = createSignUpRequest("test@example.com", "qwer1234!");
            when(memberService.existsByEmail(signUpRequest.email())).thenReturn(false);
            when(emailVerificationService.isVerifiedEmail(signUpRequest.email())).thenReturn(false);

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class,
                    () -> signUpService.signUp(signUpRequest));

            assertEquals(exception.getCode(), SignUpErrorCode.NOT_VERIFIED_EMAIL.name());
        }

        @DisplayName("필수 서비스 약관에 동의하지 않은 경우 실패한다.")
        @Test
        void signUp_WhenAllRequiredServiceTermsAreNotAgreed_ShouldThrowException() {
            SignUpRequest signUpRequest = createSignUpRequest("test@example.com", "qwer1234!");
            when(memberService.existsByEmail(signUpRequest.email())).thenReturn(false);
            when(emailVerificationService.isVerifiedEmail(signUpRequest.email())).thenReturn(true);
            when(serviceTermAgreementService.isAgreedAllRequiredServiceTerms(
                signUpRequest.serviceTerms())).thenReturn(false);

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class,
                    () -> signUpService.signUp(signUpRequest));

            assertEquals(exception.getCode(),
                SignUpErrorCode.NOT_AGREED_REQUIRED_SERVICE_TERM.name());
        }
    }
}