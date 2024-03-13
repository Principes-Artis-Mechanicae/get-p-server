package es.princip.getp.domain.auth.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Nested
    @DisplayName("sendEmail()은")
    class SendEmail {
        @DisplayName("이메일을 전송한다.")
        @Test
        void sendEmail() {
            String email = "test@example.com";
            String title = "title";
            String text = "text";
            SimpleMailMessage emailForm = new SimpleMailMessage();
            emailForm.setTo(email);
            emailForm.setSubject(title);
            emailForm.setText(text);

            emailService.sendEmail(email, title, text);

            verify(emailSender, times(1)).send(eq(emailForm));
        }
    }
}
