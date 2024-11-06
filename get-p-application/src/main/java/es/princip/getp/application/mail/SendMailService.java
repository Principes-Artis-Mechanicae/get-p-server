package es.princip.getp.application.mail;

import es.princip.getp.application.mail.command.SendMailCommand;
import es.princip.getp.application.mail.port.in.SendMailUseCase;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class SendMailService implements SendMailUseCase {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.templates.logo}")
    private Resource logo;

    @Async
    public void send(final SendMailCommand command) throws MessagingException {
        final MimeMessage message = messageFrom(command);
        mailSender.send(message);
    }

    private MimeMessage messageFrom(final SendMailCommand command) throws MessagingException {
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        helper.setTo(command.email().getValue());
        helper.setSubject(command.title());
        helper.setText(command.text(), true);
        helper.addInline("logo", logo);
        return mimeMessage;
    }
}