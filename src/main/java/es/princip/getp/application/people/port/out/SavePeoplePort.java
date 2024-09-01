package es.princip.getp.application.people.port.out;

import es.princip.getp.domain.people.command.domain.People;

public interface SavePeoplePort {

    Long save(People people);
}
