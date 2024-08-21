package es.princip.getp.application.client.service;

import es.princip.getp.application.client.port.in.RemoveClientUseCase;
import es.princip.getp.application.client.port.out.DeleteClientPort;
import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.domain.client.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RemoveRegisterClientService implements RemoveClientUseCase {

    private final LoadClientPort loadClientPort;
    private final DeleteClientPort deleteClientPort;

    @Override
    @Transactional
    public void remove(Long memberId) {
        final Client client = loadClientPort.loadBy(memberId);
        deleteClientPort.delete(client);
    }
}