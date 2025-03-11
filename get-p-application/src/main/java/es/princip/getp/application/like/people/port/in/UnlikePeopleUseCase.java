package es.princip.getp.application.like.people.port.in;

import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;

public interface UnlikePeopleUseCase {

    void unlike(MemberId memberId, PeopleId peopleId);
}
