package es.princip.getp.domain.client.query.dao;

import es.princip.getp.domain.client.query.dto.ClientResponse;

public interface ClientDao {

    ClientResponse findById(final Long clientId);

    ClientResponse findByMemberId(final Long memberId);
}
