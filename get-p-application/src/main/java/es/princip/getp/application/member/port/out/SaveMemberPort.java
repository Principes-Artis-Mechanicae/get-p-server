package es.princip.getp.application.member.port.out;

import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;

public interface SaveMemberPort {

    MemberId save(Member member);
}
