package es.princip.getp.application.client.port.in;

import es.princip.getp.application.client.command.RegisterClientCommand;
import es.princip.getp.domain.client.model.ClientId;

public interface RegisterClientUseCase {

    ClientId register(RegisterClientCommand command);
}
