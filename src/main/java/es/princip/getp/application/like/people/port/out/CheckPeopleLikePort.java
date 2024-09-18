package es.princip.getp.application.like.people.port.out;

import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.people.model.PeopleId;

public interface CheckPeopleLikePort {

    boolean existsBy(ClientId clientId, PeopleId peopleId);
}
