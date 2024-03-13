package es.princip.getp.domain.auth.service;

import static es.princip.getp.fixture.EmailVerificationFixture.createEmailVerification;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.princip.getp.domain.auth.domain.entity.EmailVerification;
import es.princip.getp.domain.auth.exception.EmailVerificationErrorCode;
import es.princip.getp.domain.auth.repository.EmailVerificationRepository;
import es.princip.getp.global.exception.BusinessLogicException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmailVerificationServiceTest {
    @Mock
    private EmailService emailService;

    @Mock
    private EmailVerificationRepository emailVerificationRepository;

    @InjectMocks
    private EmailVerificationService emailVerificationService;

    @Nested
    @DisplayName("sendVerificationCode()는")
    class SendVerificationCode {
        @DisplayName("이메일 인증 코드를 전송한다.")
        @Test
        void sendVerificationCode() {
            String email = "test@example.com";

            emailVerificationService.sendEmailVerificationCode(email);

            verify(emailService, times(1)).sendEmail(eq(email), any(), any());
            verify(emailVerificationRepository, times(1)).save(any(EmailVerification.class));
        }

        @DisplayName("이메일 인증 코드를 중복해서 전송하는 경우 기존의 이메일 인증 코드는 삭제한다.")
        @Test
        void sendVerificationCodeSeveralTimes() {
            String email = "test@example.com";
            String oldVerificationCode = "1234";
            EmailVerification emailVerification = createEmailVerification(email, oldVerificationCode);
            when(emailVerificationRepository.findById(email)).thenReturn(Optional.of(emailVerification));

            emailVerificationService.sendEmailVerificationCode(email);

            verify(emailVerificationRepository, times(1)).deleteByEmail(email);
            verify(emailService, times(1)).sendEmail(eq(email), any(), any());
            verify(emailVerificationRepository, times(1)).save(any(EmailVerification.class));
        }
    }

    @Nested
    @DisplayName("verifyEmail()은")
    class VerifyEmail {
        @DisplayName("이메일을 인증한다.")
        @Test
        void verifyEmail() {
            String email = "test@example.com";
            String verificationCode = "1234";
            EmailVerification emailVerification = createEmailVerification(email, verificationCode);
            when(emailVerificationRepository.findById(email)).thenReturn(Optional.of(emailVerification));

            emailVerificationService.verifyEmail(email, verificationCode);

            verify(emailVerificationRepository, times(1)).delete(emailVerification);
        }

        @DisplayName("유효하지 않은 이메일 인증인 경우 실패한다.")
        @Test
        void verifyEmail_WhenEmailVerificationIsInvalid_ShouldThrowException() {
            String email = "test@example.com";
            when(emailVerificationRepository.findById(email)).thenReturn(Optional.empty());

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class, () -> emailVerificationService.verifyEmail(email, "1234"));
            assertEquals(exception.getErrorCode(), EmailVerificationErrorCode.INVALID_EMAIL_VERIFICATION);
        }

        @DisplayName("이메일 인증 코드가 일치하지 않은 경우 실패한다.")
        @Test
        void verifyEmail_WhenVerificationCodeIsIncorrect_ShouldThrowException() {
            String email = "test@example.com";
            String verificationCode = "5678";
            EmailVerification emailVerification = createEmailVerification(email, verificationCode);
            when(emailVerificationRepository.findById(email)).thenReturn(Optional.of(emailVerification));

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class, () -> emailVerificationService.verifyEmail(email, "1234"));
            assertEquals(exception.getErrorCode(), EmailVerificationErrorCode.INCORRECT_EMAIL_VERIFICATION_CODE);
        }
    }
}
