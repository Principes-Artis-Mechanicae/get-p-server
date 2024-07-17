package es.princip.getp.domain.client.command.application;

import es.princip.getp.domain.client.command.application.command.CreateClientCommand;
import es.princip.getp.domain.client.command.application.command.UpdateClientCommand;
import es.princip.getp.domain.client.command.domain.Client;
import es.princip.getp.domain.client.command.domain.ClientRepository;
import es.princip.getp.domain.member.application.MemberService;
import es.princip.getp.domain.member.application.command.UpdateMemberCommand;
import es.princip.getp.domain.member.domain.model.Email;
import es.princip.getp.domain.member.domain.model.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClientService {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public Long create(CreateClientCommand command) {
        memberService.update(UpdateMemberCommand.from(command));
        // 이메일이 입력되지 않은 경우 회원 가입 시 작성한 이메일 주소를 기본값으로 사용
        Email email = command.email();
        if (email == null) {
            email = memberRepository.findById(command.memberId()).orElseThrow().getEmail();
        }
        Client client = Client.builder()
            .email(email)
            .bankAccount(command.bankAccount())
            .address(command.address())
            .memberId(command.memberId())
            .build();
        return clientRepository.save(client).getClientId();
    }

    @Transactional
    public void update(UpdateClientCommand command) {
        memberService.update(UpdateMemberCommand.from(command));
        Client client = clientRepository.findByMemberId(command.memberId()).orElseThrow();
        client.edit(command.email(), command.address(), command.bankAccount());
    }

    @Transactional
    public void delete(Long memberId) {
        Client client = clientRepository.findByMemberId(memberId).orElseThrow();
        clientRepository.delete(client);
    }
}