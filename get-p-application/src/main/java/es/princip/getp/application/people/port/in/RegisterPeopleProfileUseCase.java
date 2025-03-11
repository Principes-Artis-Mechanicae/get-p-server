package es.princip.getp.application.people.port.in;

import es.princip.getp.application.people.dto.command.RegisterPeopleProfileCommand;

public interface RegisterPeopleProfileUseCase {

    void register(RegisterPeopleProfileCommand command);
}
