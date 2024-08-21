package es.princip.getp.application.client.port.out;

import es.princip.getp.api.controller.client.query.dto.ClientResponse;

public interface ClientQuery {

    ClientResponse findById(final Long clientId);

    ClientResponse findByMemberId(final Long memberId);
}
