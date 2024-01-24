package es.princip.getp.domain.client.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.princip.getp.domain.client.dto.request.CreateClientRequest;
import es.princip.getp.domain.client.dto.request.UpdateClientRequest;
import es.princip.getp.domain.client.entity.Client;
import es.princip.getp.domain.client.exception.ClientErrorCode;
import es.princip.getp.domain.client.repository.ClientRepository;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.global.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    
    private Client get(Optional<Client> client) {
        return client.orElseThrow(() -> new BusinessLogicException(ClientErrorCode.CLIENT_NOT_FOUND));
    }

    public Client getByMemberId(Long memberId) {
        return get(clientRepository.findByMember_MemberId(memberId));
    }

    public Client getByClientId(Long clientId) {
        return get(clientRepository.findById(clientId));
    }

    @Transactional
    public Client create(Member member, CreateClientRequest request) {
        Client client = request.toEntity(member);
        return clientRepository.save(client);
    }

    @Transactional
    public Client update(Long memberId, UpdateClientRequest request) {
        Client client = getByMemberId(memberId);
        client.update(request);
        return client;
    }

    @Transactional
    public void delete(Long memberId) {
        Client client = getByMemberId(memberId);
        clientRepository.delete(client);
    }
}