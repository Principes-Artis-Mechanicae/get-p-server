package es.princip.getp.application.people.service;

import es.princip.getp.application.member.command.EditMemberCommand;
import es.princip.getp.application.member.port.in.EditMemberUseCase;
import es.princip.getp.application.people.command.EditPeopleCommand;
import es.princip.getp.application.people.port.in.EditPeopleUseCase;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.exception.NotFoundPeopleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EditPeopleService implements EditPeopleUseCase {

    private final EditMemberUseCase editMemberUseCase;
    private final LoadPeoplePort loadPeoplePort;

    /**
     * 피플 정보를 수정한다.
     *
     * @param command 피플 정보 수정 명령
     * @throws NotFoundPeopleException 회원의 피플 정보를 등록하지 않은 경우
     */
    @Transactional
    public void edit(final EditPeopleCommand command) {
        editMemberUseCase.editMember(EditMemberCommand.from(command));
        final People people = loadPeoplePort.loadBy(command.memberId());
        people.editInfo(command.email(), command.peopleType());
    }
}