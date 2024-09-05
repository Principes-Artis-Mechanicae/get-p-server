package es.princip.getp.fixture.like;

import es.princip.getp.domain.like.people.model.PeopleLike;

public class PeopleLikeFixture {
    public static PeopleLike peopleLike(Long clientId, Long peopleId) {
        return PeopleLike.builder()
            .clientId(clientId)
            .peopleId(peopleId)
            .build();
    }
}
