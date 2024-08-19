package es.princip.getp.domain.member.command.application.port.out;

import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.domain.member.command.domain.model.Member;

public interface LoadMemberPort {

    Member loadBy(Email email);

    Member loadBy(Long id);
}
