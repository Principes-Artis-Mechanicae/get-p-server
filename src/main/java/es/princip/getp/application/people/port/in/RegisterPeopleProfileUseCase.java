package es.princip.getp.application.people.port.in;

import es.princip.getp.application.people.command.RegisterPeopleProfileCommand;

public interface RegisterPeopleProfileUseCase {

    void register(RegisterPeopleProfileCommand command);
}
