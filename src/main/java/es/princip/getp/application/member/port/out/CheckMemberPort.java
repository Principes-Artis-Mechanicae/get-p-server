package es.princip.getp.application.member.port.out;

import es.princip.getp.domain.common.model.Email;

public interface CheckMemberPort {

    boolean existsBy(Email email);
}
