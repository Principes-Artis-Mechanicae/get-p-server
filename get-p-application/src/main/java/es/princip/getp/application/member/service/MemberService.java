package es.princip.getp.application.member.service;

import es.princip.getp.application.member.dto.command.EditMemberCommand;
import es.princip.getp.application.member.port.in.EditMemberUseCase;
import es.princip.getp.application.member.port.out.LoadMemberPort;
import es.princip.getp.application.member.port.out.UpdateMemberPort;
import es.princip.getp.domain.member.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements EditMemberUseCase {

    private final UpdateMemberPort updateMemberPort;
    private final LoadMemberPort loadMemberPort;

    @Override
    @Transactional
    public void editMember(final EditMemberCommand command) {
        final Member member = loadMemberPort.loadBy(command.memberId());
        member.edit(command.nickname(), command.phoneNumber());
        updateMemberPort.update(member);
    }
}
