package es.princip.getp.application.like.people.port.out;

import es.princip.getp.domain.like.people.model.PeopleLike;
import es.princip.getp.domain.people.model.PeopleId;

public interface LoadPeopleLikePort {

    PeopleLike loadBy(Long clientId, PeopleId peopleId);
}
