package es.princip.getp.domain.auth.application;

import es.princip.getp.domain.auth.application.command.SignUpCommand;
import es.princip.getp.domain.auth.exception.SignUpErrorCode;
import es.princip.getp.domain.member.command.application.MemberService;
import es.princip.getp.domain.member.command.application.command.CreateMemberCommand;
import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.domain.member.command.domain.model.Member;
import es.princip.getp.domain.member.command.domain.model.MemberRepository;
import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.infra.exception.BusinessLogicException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static es.princip.getp.domain.auth.fixture.EmailVerificationFixture.VERIFICATION_CODE;
import static es.princip.getp.domain.member.fixture.EmailFixture.EMAIL;
import static es.princip.getp.domain.member.fixture.EmailFixture.email;
import static es.princip.getp.domain.member.fixture.MemberFixture.member;
import static es.princip.getp.domain.member.fixture.PasswordFixture.password;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignUpServiceTest {

    @Mock
    private VerificationService emailVerificationService;

    @Mock
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

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
            Email email = Email.of(EMAIL);
            given(memberRepository.existsByEmail(email)).willReturn(false);

            signUpService.sendEmailVerificationCodeForSignUp(email);

            verify(emailVerificationService, times(1)).sendVerificationCode(email);
        }

        @Test
        @DisplayName("이미 가입된 이메일인 경우 실패한다.")
        void sendEmailVerificationCodeForSignUp_WhenEmailIsDuplicated_ShouldThrowException() {
            Email email = Email.of(EMAIL);
            given(memberRepository.existsByEmail(email)).willReturn(true);

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class,
                    () -> signUpService.sendEmailVerificationCodeForSignUp(email));
            assertThat(exception.getErrorCode()).isEqualTo(SignUpErrorCode.DUPLICATED_EMAIL);
        }
    }

    @Nested
    @DisplayName("signUp()은")
    class SignUp {

        private final Long memberId = 1L;
        private final SignUpCommand command = new SignUpCommand(
            email(), password(), VERIFICATION_CODE, List.of(), MemberType.ROLE_PEOPLE
        );

        @DisplayName("회원 가입을 진행한다.")
        @Test
        void signUp() {
            Member member = spy(member(command.memberType()));
            given(memberService.create(any(CreateMemberCommand.class))).willReturn(memberId);
            given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

            signUpService.signUp(command);

            verify(emailVerificationService, times(1))
                .verifyEmail(command.email(), command.verificationCode());
            verify(member, times(1)).encodePassword(passwordEncoder);
        }

        @DisplayName("이메일이 인증되지 않은 경우 실패한다.")
        @Test
        void signUp_WhenEmailIsNotVerified_ShouldThrowException() {
            doThrow(new BusinessLogicException(SignUpErrorCode.NOT_VERIFIED_EMAIL))
                .when(emailVerificationService)
                .verifyEmail(command.email(), command.verificationCode());

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class,
                    () -> signUpService.signUp(command));

            assertThat(exception.getErrorCode()).isEqualTo(SignUpErrorCode.NOT_VERIFIED_EMAIL);
        }
    }
}