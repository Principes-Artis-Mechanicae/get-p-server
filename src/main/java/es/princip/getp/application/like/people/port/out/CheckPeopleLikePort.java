package es.princip.getp.application.like.people.port.out;

import es.princip.getp.domain.people.model.PeopleId;

public interface CheckPeopleLikePort {

    boolean existsBy(Long clientId, PeopleId peopleId);
}
