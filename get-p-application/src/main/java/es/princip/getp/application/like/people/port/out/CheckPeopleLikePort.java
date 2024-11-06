package es.princip.getp.application.like.people.port.out;

import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;

public interface CheckPeopleLikePort {

    boolean existsBy(MemberId memberId, PeopleId peopleId);

    Boolean existsBy(Member member, PeopleId peopleId);
}
