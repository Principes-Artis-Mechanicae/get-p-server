package es.princip.getp.application.people;

import es.princip.getp.application.people.command.RegisterPeopleProfileCommand;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleProfileData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegisterPeopleProfileUseCase implements es.princip.getp.application.people.port.in.RegisterPeopleProfileUseCase {

    private final LoadPeoplePort loadPeoplePort;

    private final PeopleDataMapper peopleDataMapper;

    @Transactional
    public void register(final RegisterPeopleProfileCommand command) {
        final Long memberId = command.memberId();
        final People people = loadPeoplePort.loadBy(memberId);
        final PeopleProfileData data = peopleDataMapper.mapToData(command);
        people.registerProfile(data);
    }
}
