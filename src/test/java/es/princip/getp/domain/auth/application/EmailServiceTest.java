package es.princip.getp.domain.auth.application;

import es.princip.getp.domain.member.domain.model.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static es.princip.getp.domain.member.fixture.EmailFixture.email;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
            Email email = email();
            String title = "title";
            String text = "text";

            emailService.sendEmail(email, title, text);

            verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
        }
    }
}
