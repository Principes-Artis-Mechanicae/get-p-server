package es.princip.getp.application.people.port.in;

import es.princip.getp.application.people.command.EditPeopleCommand;

public interface EditPeopleUseCase {

    void edit(EditPeopleCommand command);
}
