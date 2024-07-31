package es.princip.getp.domain.auth.application;

import es.princip.getp.domain.auth.domain.EmailVerification;
import es.princip.getp.domain.auth.domain.EmailVerificationRepository;
import es.princip.getp.domain.auth.exception.EmailVerificationErrorCode;
import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.infra.exception.BusinessLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static es.princip.getp.domain.auth.fixture.EmailVerificationFixture.emailVerification;
import static es.princip.getp.domain.member.fixture.EmailFixture.email;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailVerificationServiceTest {

    @Mock
    private VerificationCodeSender emailService;

    @Mock
    private EmailVerificationRepository emailVerificationRepository;

    @InjectMocks
    private VerificationService emailVerificationService;

    @BeforeEach
    void beforeAll() {
        emailVerificationService = new VerificationService(
            emailService,
            emailVerificationRepository,
            1000L,
            4
        );
    }

    @Nested
    @DisplayName("sendVerificationCode()는")
    class SendVerificationCode {
        @DisplayName("이메일 인증 코드를 전송한다.")
        @Test
        void sendVerificationCode() {
            final Email email = email();

            emailVerificationService.sendVerificationCode(email);

            verify(emailService, times(1)).send(eq(email), anyString());
            verify(emailVerificationRepository, times(1)).save(any(EmailVerification.class));
        }

        @DisplayName("이메일 인증 코드를 중복해서 전송하는 경우 기존의 이메일 인증 코드는 삭제한다.")
        @Test
        void sendVerificationCodeSeveralTimes() {
            final Email email = email();
            EmailVerification emailVerification = spy(EmailVerification.class);
            when(emailVerificationRepository.findById(email.getValue())).thenReturn(Optional.of(emailVerification));

            emailVerificationService.sendVerificationCode(email);

            verify(emailVerificationRepository, times(1)).deleteById(email.getValue());
            verify(emailService, times(1)).send(eq(email), anyString());
            verify(emailVerificationRepository, times(1)).save(any(EmailVerification.class));
        }
    }

    @Nested
    @DisplayName("verifyEmail()은")
    class VerifyEmail {
        @DisplayName("이메일을 인증한다.")
        @Test
        void verifyEmail() {
            final Email email = email();
            String verificationCode = "1234";
            EmailVerification emailVerification = emailVerification(email, verificationCode);
            when(emailVerificationRepository.findById(email.getValue())).thenReturn(Optional.of(emailVerification));

            emailVerificationService.verifyEmail(email, verificationCode);

            verify(emailVerificationRepository, times(1)).delete(emailVerification);
        }

        @DisplayName("유효하지 않은 이메일 인증인 경우 실패한다.")
        @Test
        void verifyEmail_WhenEmailVerificationIsInvalid_ShouldThrowException() {
            final Email email = email();
            when(emailVerificationRepository.findById(email.getValue())).thenReturn(Optional.empty());

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class, () -> emailVerificationService.verifyEmail(email, "1234"));
            assertEquals(exception.getErrorCode(), EmailVerificationErrorCode.INVALID_EMAIL_VERIFICATION);
        }

        @DisplayName("이메일 인증 코드가 일치하지 않은 경우 실패한다.")
        @Test
        void verifyEmail_WhenVerificationCodeIsIncorrect_ShouldThrowException() {
            final Email email = email();
            String verificationCode = "5678";
            EmailVerification emailVerification = emailVerification(email, verificationCode);
            when(emailVerificationRepository.findById(email.getValue())).thenReturn(Optional.of(emailVerification));

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class, () -> emailVerificationService.verifyEmail(email, "1234"));
            assertEquals(exception.getErrorCode(), EmailVerificationErrorCode.INCORRECT_EMAIL_VERIFICATION_CODE);
        }
    }
}
