package es.princip.getp.domain.client.application;

import es.princip.getp.domain.client.application.command.CreateClientCommand;
import es.princip.getp.domain.client.application.command.UpdateClientCommand;
import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.client.domain.ClientRepository;
import es.princip.getp.domain.member.application.MemberService;
import es.princip.getp.domain.member.application.command.UpdateMemberCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClientService {

    private final MemberService memberService;
    private final ClientRepository clientRepository;

    @Transactional
    public Long create(CreateClientCommand command) {
        memberService.update(UpdateMemberCommand.from(command));
        Client client = Client.builder()
            .bankAccount(command.bankAccount())
            .address(command.address())
            .memberId(command.memberId())
            .build();
        clientRepository.save(client);
        return client.getClientId();
    }

    @Transactional
    public void update(UpdateClientCommand command) {
        memberService.update(UpdateMemberCommand.from(command));
        Client client = clientRepository.findByMemberId(command.memberId()).orElseThrow();
        client.changeAddress(command.address());
        client.changeBankAccount(command.bankAccount());
        client.changeEmail(command.email());
    }

    @Transactional
    public void delete(Long memberId) {
        Client client = clientRepository.findByMemberId(memberId).orElseThrow();
        clientRepository.delete(client);
    }
}