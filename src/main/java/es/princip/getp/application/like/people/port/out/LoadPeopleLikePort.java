package es.princip.getp.application.like.people.port.out;

import es.princip.getp.domain.like.people.model.PeopleLike;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;

public interface LoadPeopleLikePort {

    PeopleLike loadBy(MemberId memberId, PeopleId peopleId);
}
