package es.princip.getp.persistence.adapter.client;

import es.princip.getp.application.client.port.out.*;
import es.princip.getp.domain.client.model.Client;
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
    public boolean existsBy(final Long memberId) {
        return clientJpaRepository.existsByMemberId(memberId);
    }

    @Override
    public void delete(final Client client) {
        clientJpaRepository.deleteById(client.getId());
    }

    @Override
    public Client loadBy(final Long memberId) {
        return mapper.mapToDomain(clientJpaRepository.findByMemberId(memberId)
            .orElseThrow(NotFoundClientException::new));
    }

    @Override
    public Long save(final Client client) {
        return clientJpaRepository.save(mapper.mapToJpa(client))
            .getClientId();
    }

    @Override
    public void update(final Client client) {
        if (!clientJpaRepository.existsById(client.getId())) {
            throw new NotFoundClientException();
        }
        save(client);
    }
}
