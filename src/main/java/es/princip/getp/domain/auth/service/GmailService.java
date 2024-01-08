package es.princip.getp.domain.auth.service;

import org.springframework.context.annotation.Primary;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import es.princip.getp.domain.auth.exception.EmailServerUnavailableException;
import es.princip.getp.domain.auth.exception.WrongEmailException;
import es.princip.getp.global.util.EmailUtil;
import lombok.RequiredArgsConstructor;

@Primary
@RequiredArgsConstructor
@Service
public class GmailService implements EmailService {
    private final JavaMailSender emailSender;
    
    private SimpleMailMessage createEmailForm(String email, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(title);
        message.setText(text);
        return message;
    }

    public void sendEmail(String email, String title, String text) {
        if (!EmailUtil.isValidEmail(email)) {
            throw new WrongEmailException();
        }
        SimpleMailMessage emailForm = createEmailForm(email, title, text);
        try {
            emailSender.send(emailForm);
        } catch (MailException mailException) {
            throw new EmailServerUnavailableException();
        }
    }
}
