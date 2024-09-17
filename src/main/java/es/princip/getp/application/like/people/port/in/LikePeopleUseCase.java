package es.princip.getp.application.like.people.port.in;

import es.princip.getp.domain.member.model.MemberId;

public interface LikePeopleUseCase {

    void like(MemberId memberId, Long peopleId);
}
