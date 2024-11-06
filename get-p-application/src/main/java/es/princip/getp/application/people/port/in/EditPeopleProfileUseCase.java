package es.princip.getp.application.people.port.in;

import es.princip.getp.application.people.dto.command.EditPeopleProfileCommand;

public interface EditPeopleProfileUseCase {

    void edit(EditPeopleProfileCommand command);
}
