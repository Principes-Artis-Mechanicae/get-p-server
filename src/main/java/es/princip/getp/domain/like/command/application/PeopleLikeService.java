package es.princip.getp.domain.like.command.application;

import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.like.command.domain.people.PeopleLiker;
import es.princip.getp.domain.like.command.domain.people.PeopleUnliker;
import es.princip.getp.domain.people.model.People;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PeopleLikeService {

    private final PeopleLiker peopleLiker;
    private final PeopleUnliker peopleUnliker;

    private final LoadClientPort loadClientPort;
    private final LoadPeoplePort loadPeoplePort;

    @Transactional
    public void like(final Long memberId, final Long peopleId) {
        final Client client = loadClientPort.loadBy(memberId);
        final People people = loadPeoplePort.loadByPeopleId(peopleId);
        peopleLiker.like(client, people);
    }

    @Transactional
    public void unlike(final Long memberId, final Long peopleId) {
        final Client client = loadClientPort.loadBy(memberId);
        final People people = loadPeoplePort.loadByPeopleId(peopleId);
        peopleUnliker.unlike(client, people);
    }
}
