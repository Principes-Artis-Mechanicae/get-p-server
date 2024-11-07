package es.princip.getp.fixture.like;

import es.princip.getp.domain.like.people.model.PeopleLike;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;

public class PeopleLikeFixture {
    public static PeopleLike peopleLike(MemberId memberId, PeopleId peopleId) {
        return PeopleLike.builder()
            .memberId(memberId)
            .peopleId(peopleId)
            .build();
    }
}
