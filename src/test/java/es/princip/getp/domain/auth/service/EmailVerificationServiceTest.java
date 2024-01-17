package es.princip.getp.domain.auth.service;

import static es.princip.getp.domain.auth.fixture.EmailVerificationFixture.createEmailVerification;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.princip.getp.domain.auth.entity.EmailVerification;
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

        @DisplayName("사용자가 이메일 인증 코드 유효 시간 내에 재요청한 경우 실패한다.")
        @Test
        void sendVerificationCode_WhenEmailVerificationCodeIsAlreadySended_ShouldThrowException() {
            String email = "test@example.com";
            when(emailVerificationRepository.findById(email)).thenReturn(Optional.of(new EmailVerification(email, "1234")));

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class, () -> emailVerificationService.sendEmailVerificationCode(email));
            assertEquals(exception.getCode(), EmailVerificationErrorCode.ALREADY_EMAIL_VERIFICATION_CODE_SENDED.name());
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

            assertTrue(emailVerification.isVerified());
            verify(emailVerificationRepository, times(1)).save(emailVerification);
        }

        @DisplayName("유효하지 않은 이메일 인증인 경우 실패한다.")
        @Test
        void verifyEmail_WhenEmailVerificationIsInvalid_ShouldThrowException() {
            String email = "test@example.com";
            when(emailVerificationRepository.findById(email)).thenReturn(Optional.empty());

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class, () -> emailVerificationService.verifyEmail(email, "1234"));
            assertEquals(exception.getCode(), EmailVerificationErrorCode.INVALID_EMAIL_VERIFICATION.name());
        }

        @DisplayName("사용자가 이메일 인증 유효 시간 내에 재요청한 경우 실패한다.")
        @Test
        void verifyEmail_WhenEmailIsAlreadyVerified_ShouldThrowException() {
            String email = "test@example.com";
            String verificationCode = "1234";
            EmailVerification emailVerification = createEmailVerification(email, verificationCode);
            when(emailVerificationRepository.findById(email)).thenReturn(Optional.of(emailVerification));
            emailVerificationService.verifyEmail(email, verificationCode);

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class, () -> emailVerificationService.verifyEmail(email, verificationCode));
            assertEquals(exception.getCode(), EmailVerificationErrorCode.ALREADY_VERIFIED_EMAIL.name());
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
            assertEquals(exception.getCode(), EmailVerificationErrorCode.INCORRECT_EMAIL_VERIFICATION_CODE.name());
        }
    }

    @Nested
    @DisplayName("isVerifiedEmail()은")
    class IsVerifiedEmail {
        @DisplayName("이메일이 인증된 경우 true를 반환한다.")
        @Test
        void isVerifiedEmail_WhenEmailIsVerified_ShouldReturnTrue() {
            String email = "test@example.com";
            String verificationCode = "1234";
            EmailVerification emailVerification = createEmailVerification(email, verificationCode);
            emailVerification.verify(verificationCode);
            when(emailVerificationRepository.findById(email)).thenReturn(Optional.of(emailVerification));

            assertTrue(emailVerificationService.isVerifiedEmail(email));
        }

        @DisplayName("이메일이 인증되지 않은 경우 false를 반환한다.")
        @Test
        void isVerifiedEmail_WhenEmailIsNotVerified_ShouldReturnFalse() {
            String email = "test@example.com";
            EmailVerification emailVerification = createEmailVerification(email, "1234");
            when(emailVerificationRepository.findById(email)).thenReturn(Optional.of(emailVerification));

            assertFalse(emailVerificationService.isVerifiedEmail(email));
        }
    }
}
