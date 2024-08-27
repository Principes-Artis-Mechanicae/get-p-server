package es.princip.getp.application.client.service;

import es.princip.getp.application.client.command.EditClientCommand;
import es.princip.getp.application.client.port.in.EditClientUseCase;
import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.application.client.port.out.UpdateClientPort;
import es.princip.getp.application.member.command.EditMemberCommand;
import es.princip.getp.application.member.port.in.EditMemberUseCase;
import es.princip.getp.domain.client.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EditClientService implements EditClientUseCase {

    private final EditMemberUseCase editMemberUseCase;

    private final LoadClientPort loadClientPort;
    private final UpdateClientPort updateClientPort;

    @Override
    @Transactional
    public void edit(EditClientCommand command) {
        editMemberUseCase.editMember(EditMemberCommand.from(command));
        final Client client = loadClientPort.loadBy(command.memberId());
        client.edit(command.email(), command.address(), command.bankAccount());
        updateClientPort.update(client);
    }
}