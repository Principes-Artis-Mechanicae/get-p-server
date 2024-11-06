package es.princip.getp.application.client.port.in;

import es.princip.getp.application.client.dto.command.EditClientCommand;

public interface EditClientUseCase {

    void edit(EditClientCommand command);
}
