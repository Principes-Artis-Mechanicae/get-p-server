package es.princip.getp.infra;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import es.princip.getp.domain.auth.exception.EmailErrorCode;
import es.princip.getp.infra.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailSender {

    private final JavaMailSender mailSender;

    @Async
    public void send(SimpleMailMessage message) {
        try {
            mailSender.send(message);
        } catch (MailException mailException) {
            log.error(mailException.getMessage());
            throw new BusinessLogicException(EmailErrorCode.EMAIL_SERVER_UNAVAILABLE);
        }
    }

    public static SimpleMailMessage createSimpleMailMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }
}