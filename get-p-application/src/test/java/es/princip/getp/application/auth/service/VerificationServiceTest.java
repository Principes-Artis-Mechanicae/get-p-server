package es.princip.getp.application.auth.service;

import es.princip.getp.application.auth.exception.IncorrectVerificationCodeException;
import es.princip.getp.application.auth.exception.NotFoundVerificationException;
import es.princip.getp.domain.auth.EmailVerification;
import es.princip.getp.domain.common.model.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static es.princip.getp.fixture.auth.EmailVerificationFixture.emailVerification;
import static es.princip.getp.fixture.common.EmailFixture.email;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerificationServiceTest {

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
            final String verificationCode = "1234";
            final EmailVerification emailVerification = emailVerification(email, verificationCode);
            given(emailVerificationRepository.findById(email.getValue()))
                .willReturn(emailVerification);

            emailVerificationService.verifyEmail(email, verificationCode);

            verify(emailVerificationRepository, times(1)).deleteById(email.getValue());
        }

        @DisplayName("유효하지 않은 이메일 인증인 경우 실패한다.")
        @Test
        void verifyEmail_WhenEmailVerificationIsInvalid_ShouldThrowException() {
            final Email email = email();
            given(emailVerificationRepository.findById(email.getValue()))
                .willThrow(NotFoundVerificationException.class);

            assertThatCode(() -> emailVerificationService.verifyEmail(email, "1234"))
                .isInstanceOf(NotFoundVerificationException.class);
        }

        @DisplayName("이메일 인증 코드가 일치하지 않은 경우 실패한다.")
        @Test
        void verifyEmail_WhenVerificationCodeIsIncorrect_ShouldThrowException() {
            final Email email = email();
            final String verificationCode = "5678";
            final EmailVerification emailVerification = emailVerification(email, verificationCode);
            given(emailVerificationRepository.findById(email.getValue()))
                .willReturn(emailVerification);

            assertThatCode(() -> emailVerificationService.verifyEmail(email, "1234"))
                .isInstanceOf(IncorrectVerificationCodeException.class);
        }
    }
}
