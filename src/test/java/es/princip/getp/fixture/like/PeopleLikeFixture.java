package es.princip.getp.fixture.like;

import es.princip.getp.domain.like.people.model.PeopleLike;
import es.princip.getp.domain.people.model.PeopleId;

public class PeopleLikeFixture {
    public static PeopleLike peopleLike(Long clientId, PeopleId peopleId) {
        return PeopleLike.builder()
            .clientId(clientId)
            .peopleId(peopleId)
            .build();
    }
}
