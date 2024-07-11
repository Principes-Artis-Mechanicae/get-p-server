package es.princip.getp.domain.auth.service;

import es.princip.getp.domain.auth.exception.EmailErrorCode;
import es.princip.getp.infra.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;
    
    private SimpleMailMessage createEmailForm(String email, String title, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(title);
        message.setText(text);
        return message;
    }

    public void sendEmail(String email, String title, String text) {
        SimpleMailMessage emailForm = createEmailForm(email, title, text);
        try {
            emailSender.send(emailForm);
        } catch (MailException mailException) {
            log.error(mailException.getMessage());
            throw new BusinessLogicException(EmailErrorCode.EMAIL_SERVER_UNAVAILABLE);
        }
    }
}
