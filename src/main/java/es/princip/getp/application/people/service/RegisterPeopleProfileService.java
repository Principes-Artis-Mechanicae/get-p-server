package es.princip.getp.application.people.service;

import es.princip.getp.application.people.command.RegisterPeopleProfileCommand;
import es.princip.getp.application.people.mapper.PeopleDataMapper;
import es.princip.getp.application.people.port.in.RegisterPeopleProfileUseCase;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.application.people.port.out.UpdatePeoplePort;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleProfileData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegisterPeopleProfileService implements RegisterPeopleProfileUseCase {

    private final LoadPeoplePort loadPeoplePort;
    private final UpdatePeoplePort updatePeoplePort;

    private final PeopleDataMapper peopleDataMapper;

    @Transactional
    public void register(final RegisterPeopleProfileCommand command) {
        final Long memberId = command.memberId();
        final People people = loadPeoplePort.loadBy(memberId);
        final PeopleProfileData data = peopleDataMapper.mapToData(command);
        people.registerProfile(data);
        updatePeoplePort.update(people);
    }
}
