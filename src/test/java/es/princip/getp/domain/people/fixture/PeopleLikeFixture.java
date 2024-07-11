package es.princip.getp.domain.people.fixture;

import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleLike;

public class PeopleLikeFixture {
    public static PeopleLike createPeopleLike(final Client client, final People people) {
        return PeopleLike.builder()
            .client(client)
            .people(people)
            .build();
    }
}
