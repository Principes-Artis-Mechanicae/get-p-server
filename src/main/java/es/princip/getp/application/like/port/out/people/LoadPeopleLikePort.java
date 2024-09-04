package es.princip.getp.application.like.port.out.people;

import es.princip.getp.domain.like.model.people.PeopleLike;

public interface LoadPeopleLikePort {
    PeopleLike findByClientIdAndPeopleId(Long clientId, Long peopleId);
}
