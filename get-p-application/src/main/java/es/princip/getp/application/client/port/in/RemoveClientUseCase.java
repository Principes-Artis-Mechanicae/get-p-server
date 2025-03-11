package es.princip.getp.application.client.port.in;

import es.princip.getp.domain.member.model.MemberId;

public interface RemoveClientUseCase {

    void remove(MemberId memberId);
}
