package es.princip.getp.domain.people.command.domain;

import es.princip.getp.domain.common.annotation.DomainService;
import es.princip.getp.domain.people.exception.NotRegisteredPeopleProfileException;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class PeopleProfileChecker {

    private final PeopleProfileRepository peopleProfileRepository;

    public void checkPeopleProfileIsRegistered(final People people) {
        final Long peopleId = people.getPeopleId();
        if (!peopleProfileRepository.existsByPeopleId(peopleId)) {
            throw new NotRegisteredPeopleProfileException();
        }
    }
}
