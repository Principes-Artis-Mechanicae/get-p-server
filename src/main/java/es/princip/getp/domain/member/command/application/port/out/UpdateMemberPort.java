package es.princip.getp.domain.member.command.application.port.out;

import es.princip.getp.domain.member.command.domain.model.Member;

public interface UpdateMemberPort {

    void update(Member member);
}
