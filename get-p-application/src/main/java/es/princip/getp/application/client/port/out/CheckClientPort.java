package es.princip.getp.application.client.port.out;

import es.princip.getp.domain.member.model.MemberId;

public interface CheckClientPort {

    boolean existsBy(MemberId memberId);
}
