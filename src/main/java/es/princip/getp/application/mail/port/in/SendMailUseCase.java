package es.princip.getp.application.mail.port.in;

import es.princip.getp.application.mail.command.SendMailCommand;

public interface SendMailUseCase {

    void send(final SendMailCommand command);
}
