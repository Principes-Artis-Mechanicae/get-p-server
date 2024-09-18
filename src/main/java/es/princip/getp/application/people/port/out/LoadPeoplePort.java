package es.princip.getp.application.people.port.out;

import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;

public interface LoadPeoplePort {

    People loadBy(MemberId memberId);
    People loadBy(PeopleId peopleId);
}
