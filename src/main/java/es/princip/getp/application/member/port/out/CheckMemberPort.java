package es.princip.getp.application.member.port.out;

import es.princip.getp.domain.member.model.Email;

public interface CheckMemberPort {

    boolean existsByEmail(Email email);
}
