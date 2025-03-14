package es.princip.getp.application.people.service;

import es.princip.getp.application.member.dto.command.EditMemberCommand;
import es.princip.getp.application.member.port.in.EditMemberUseCase;
import es.princip.getp.application.people.dto.command.EditPeopleCommand;
import es.princip.getp.application.people.port.in.EditPeopleUseCase;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.application.people.port.out.UpdatePeoplePort;
import es.princip.getp.domain.people.model.People;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class EditPeopleService implements EditPeopleUseCase {

    private final EditMemberUseCase editMemberUseCase;

    private final LoadPeoplePort loadPeoplePort;
    private final UpdatePeoplePort updatePeoplePort;

    @Transactional
    public void edit(final EditPeopleCommand command) {
        editMemberUseCase.editMember(EditMemberCommand.from(command));
        final People people = loadPeoplePort.loadBy(command.memberId());
        people.editInfo(command.email());
        updatePeoplePort.update(people);
    }
}