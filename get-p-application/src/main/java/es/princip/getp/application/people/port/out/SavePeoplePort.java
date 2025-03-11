package es.princip.getp.application.people.port.out;

import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;

public interface SavePeoplePort {

    PeopleId save(People people);
}
