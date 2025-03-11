package es.princip.getp.persistence.adapter.client;

import es.princip.getp.application.client.exception.NotFoundClientException;
import es.princip.getp.application.client.port.out.*;
import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.member.model.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ClientPersistenceAdapter implements SaveClientPort,
        LoadClientPort,
        CheckClientPort,
        UpdateClientPort,
        DeleteClientPort {

    private final ClientPersistenceMapper mapper;

    private final ClientJpaRepository clientJpaRepository;

    @Override
    public boolean existsBy(final MemberId memberId) {
        return clientJpaRepository.existsByMemberId(memberId.getValue());
    }

    @Override
    public void delete(final Client client) {
        final Long id = client.getId().getValue();
        clientJpaRepository.deleteById(id);
    }

    @Override
    public Client loadBy(final MemberId memberId) {
        return mapper.mapToDomain(clientJpaRepository.findByMemberId(memberId.getValue())
            .orElseThrow(NotFoundClientException::new));
    }

    @Override
    public ClientId save(final Client client) {
        final Long id = clientJpaRepository.save(mapper.mapToJpa(client))
            .getId();
        return new ClientId(id);
    }

    @Override
    public void update(final Client client) {
        final Long id = client.getId().getValue();
        if (!clientJpaRepository.existsById(id)) {
            throw new NotFoundClientException();
        }
        save(client);
    }
}
