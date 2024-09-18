package es.princip.getp.application.like.people.port.out;

import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.like.people.model.PeopleLike;
import es.princip.getp.domain.people.model.PeopleId;

public interface LoadPeopleLikePort {

    PeopleLike loadBy(ClientId clientId, PeopleId peopleId);
}
