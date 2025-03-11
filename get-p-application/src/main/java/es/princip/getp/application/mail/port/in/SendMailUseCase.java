package es.princip.getp.application.mail.port.in;

import es.princip.getp.application.mail.command.SendMailCommand;
import jakarta.mail.MessagingException;

public interface SendMailUseCase {

    void send(final SendMailCommand command) throws MessagingException;
}
