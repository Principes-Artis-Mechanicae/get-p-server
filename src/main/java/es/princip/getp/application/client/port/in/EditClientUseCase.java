package es.princip.getp.application.client.port.in;

import es.princip.getp.application.client.command.EditClientCommand;

public interface EditClientUseCase {

    void edit(EditClientCommand command);
}
