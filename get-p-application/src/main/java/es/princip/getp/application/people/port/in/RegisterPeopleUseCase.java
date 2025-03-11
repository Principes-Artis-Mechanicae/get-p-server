package es.princip.getp.application.people.port.in;

import es.princip.getp.application.people.dto.command.RegisterPeopleCommand;
import es.princip.getp.domain.people.model.PeopleId;

public interface RegisterPeopleUseCase {

    PeopleId register(RegisterPeopleCommand command);
}
