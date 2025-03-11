package es.princip.getp.application.like.people.port.in;

import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;

public interface LikePeopleUseCase {

    void like(MemberId memberId, PeopleId peopleId);
}
