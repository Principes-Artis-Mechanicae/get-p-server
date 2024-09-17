package es.princip.getp.application.member.port.out;

import es.princip.getp.domain.common.model.Email;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;

public interface LoadMemberPort {

    Member loadBy(Email email);
    Member loadBy(MemberId memberId);
}
