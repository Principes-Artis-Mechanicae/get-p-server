package es.princip.getp.application.mail;

import es.princip.getp.application.mail.command.SendMailCommand;
import es.princip.getp.application.mail.port.in.SendMailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class SendMailService implements SendMailUseCase {

    private final MailSender mailSender;

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