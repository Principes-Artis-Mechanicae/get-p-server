package es.princip.getp.domain.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import es.princip.getp.domain.auth.exception.EmailErrorCode;
import es.princip.getp.domain.auth.service.GmailService;
import es.princip.getp.global.exception.BusinessLogicException;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @Mock
    private JavaMailSender emailSender;
    
    @InjectMocks
    private GmailService emailService;

    @DisplayName("이메일 전송")
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

    @DisplayName("잘못된 이메일 주소인 경우 예외 발생")
    @Test
    void sendEmail_WhenEmailIsWrong_ShouldThrowException() {
        String email = "testexample.com";
        String title = "title";
        String text = "text";
        SimpleMailMessage emailForm = new SimpleMailMessage();
        emailForm.setTo(email);
        emailForm.setSubject(title);
        emailForm.setText(text);

        BusinessLogicException exception = 
            assertThrows(BusinessLogicException.class, () ->  emailService.sendEmail(email, title, text));
        assertEquals(exception.getCode(), EmailErrorCode.WRONG_EMAIL.name());
    }
}
