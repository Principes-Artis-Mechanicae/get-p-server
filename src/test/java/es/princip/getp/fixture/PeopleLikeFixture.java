package es.princip.getp.fixture;

import es.princip.getp.domain.client.domain.entity.Client;
import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.domain.entity.PeopleLike;

public class PeopleLikeFixture {
    public static PeopleLike creatPeopleLike(final Client client, final People people) {
        return PeopleLike.builder()
            .client(client)
            .people(people)
            .build();
    }
}
