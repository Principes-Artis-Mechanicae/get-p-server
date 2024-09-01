package es.princip.getp.application.people.port.out;

import es.princip.getp.domain.people.model.People;

public interface LoadPeoplePort {

    People loadBy(Long memberId);
    People loadByPeopleId(Long peopleId);
}
