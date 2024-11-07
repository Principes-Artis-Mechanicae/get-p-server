package es.princip.getp.application.people.port.out;

import es.princip.getp.domain.people.model.PeopleId;

public interface DeletePeoplePort {

    void delete(PeopleId peopleId);
}
