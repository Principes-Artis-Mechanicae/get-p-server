package es.princip.getp.application.people.service;

import es.princip.getp.application.people.command.EditPeopleProfileCommand;
import es.princip.getp.application.people.mapper.PeopleDataMapper;
import es.princip.getp.application.people.port.in.EditPeopleProfileUseCase;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleProfileData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EditPeopleProfileService implements EditPeopleProfileUseCase {

    private final LoadPeoplePort loadPeoplePort;

    private final PeopleDataMapper peopleDataMapper;

    @Transactional
    public void edit(final EditPeopleProfileCommand command) {
        final Long memberId = command.memberId();
        final People people = loadPeoplePort.loadBy(memberId);
        final PeopleProfileData data = peopleDataMapper.mapToData(command);
        people.editProfile(data);
    }
}
