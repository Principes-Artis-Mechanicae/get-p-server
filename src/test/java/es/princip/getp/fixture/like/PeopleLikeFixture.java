package es.princip.getp.fixture.like;

import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.like.people.model.PeopleLike;
import es.princip.getp.domain.people.model.PeopleId;

public class PeopleLikeFixture {
    public static PeopleLike peopleLike(ClientId clientId, PeopleId peopleId) {
        return PeopleLike.builder()
            .clientId(clientId)
            .peopleId(peopleId)
            .build();
    }
}
