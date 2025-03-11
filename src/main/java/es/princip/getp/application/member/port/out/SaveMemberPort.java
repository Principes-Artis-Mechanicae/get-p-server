package es.princip.getp.application.member.port.out;

import es.princip.getp.domain.member.model.Member;

public interface SaveMemberPort {

    Long save(Member member);
}
