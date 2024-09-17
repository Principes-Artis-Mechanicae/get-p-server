package es.princip.getp.application.like.people.port.in;

import es.princip.getp.domain.member.model.MemberId;

public interface UnlikePeopleUseCase {

    void unlike(MemberId memberId, Long peopleId);
}
