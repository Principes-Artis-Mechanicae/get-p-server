package es.princip.getp.application.people.port.out;

import es.princip.getp.domain.member.model.MemberId;

public interface CheckPeoplePort {

    boolean existsBy(MemberId memberId);
}
