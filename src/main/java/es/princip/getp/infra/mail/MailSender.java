package es.princip.getp.infra.mail;

import es.princip.getp.infra.mail.command.SendMailCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailSender {

    private final JavaMailSender mailSender;

    @Async
    public void send(final SendMailCommand command) {
        final SimpleMailMessage message = from(command);
        mailSender.send(message);
    }

    private static SimpleMailMessage from(final SendMailCommand command) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(command.email().getValue());
        message.setSubject(command.title());
        message.setText(command.text());
        return message;
    }
}