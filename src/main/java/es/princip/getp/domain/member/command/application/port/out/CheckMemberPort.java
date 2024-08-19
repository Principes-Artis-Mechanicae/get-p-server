package es.princip.getp.domain.member.command.application.port.out;

import es.princip.getp.domain.member.command.domain.model.Email;

public interface CheckMemberPort {

    boolean existsByEmail(Email email);
}
