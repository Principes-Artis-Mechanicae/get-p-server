package es.princip.getp.application.client.port.in;

import es.princip.getp.application.client.command.RegisterClientCommand;

public interface RegisterClientUseCase {

    Long register(RegisterClientCommand command);
}
