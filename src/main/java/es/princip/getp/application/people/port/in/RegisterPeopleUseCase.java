package es.princip.getp.application.people.port.in;

import es.princip.getp.application.people.command.RegisterPeopleCommand;

public interface RegisterPeopleUseCase {

    Long register(RegisterPeopleCommand command);
}
