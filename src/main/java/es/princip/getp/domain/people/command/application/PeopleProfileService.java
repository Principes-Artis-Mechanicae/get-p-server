package es.princip.getp.domain.people.command.application;

import es.princip.getp.domain.people.command.application.command.EditPeopleProfileCommand;
import es.princip.getp.domain.people.command.application.command.RegisterPeopleProfileCommand;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleProfileData;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.people.exception.NotFoundPeopleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeopleProfileService {

    private final PeopleRepository peopleRepository;

    private final PeopleDataMapper peopleDataMapper;

    @Transactional
    public void registerProfile(final RegisterPeopleProfileCommand command) {
        final Long memberId = command.memberId();
        final People people = peopleRepository.findByMemberId(memberId)
            .orElseThrow(NotFoundPeopleException::new);
        final PeopleProfileData data = peopleDataMapper.mapToData(command);
        people.registerProfile(data);
    }

    @Transactional
    public void editProfile(final EditPeopleProfileCommand command) {
        final Long memberId = command.memberId();
        final People people = peopleRepository.findByMemberId(memberId)
            .orElseThrow(NotFoundPeopleException::new);
        final PeopleProfileData data = peopleDataMapper.mapToData(command);
        people.editProfile(data);
    }
}
