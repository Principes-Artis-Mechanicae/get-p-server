package es.princip.getp.application.client.port.out;

import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.member.model.MemberId;

public interface LoadClientPort {

    Client loadBy(MemberId memberId);
}
