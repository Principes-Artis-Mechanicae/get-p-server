package es.princip.getp.domain.client.query.dao;

import es.princip.getp.domain.client.query.dto.ClientResponse;

import java.util.Optional;

public interface ClientDao {

    Optional<ClientResponse> findById(final Long clientId);

    Optional<ClientResponse> findByMemberId(final Long memberId);
}
