package es.princip.getp.application.client.service;

import es.princip.getp.application.client.dto.command.RegisterClientCommand;
import es.princip.getp.application.client.exception.AlreadyExistsClientException;
import es.princip.getp.application.client.port.in.RegisterClientUseCase;
import es.princip.getp.application.client.port.out.CheckClientPort;
import es.princip.getp.application.client.port.out.SaveClientPort;
import es.princip.getp.application.member.dto.command.EditMemberCommand;
import es.princip.getp.application.member.port.in.EditMemberUseCase;
import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.common.model.Email;
import es.princip.getp.domain.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegisterClientService implements RegisterClientUseCase {

    private final EditMemberUseCase editMemberUseCase;

    private final CheckClientPort checkClientPort;
    private final SaveClientPort saveClientPort;

    @Override
    @Transactional
    public ClientId register(RegisterClientCommand command) {
        final Member member = command.member();
        if (checkClientPort.existsBy(member.getId())) {
            throw new AlreadyExistsClientException();
        }
        editMemberUseCase.editMember(EditMemberCommand.from(command));
        // 이메일이 입력되지 않은 경우 회원 가입 시 작성한 이메일 주소를 기본값으로 사용
        final Email email = command.email() == null ? member.getEmail() : command.email();
        final Client client = Client.builder()
            .email(email)
            .address(command.address())
            .memberId(member.getId())
            .build();
        return saveClientPort.save(client);
    }
}