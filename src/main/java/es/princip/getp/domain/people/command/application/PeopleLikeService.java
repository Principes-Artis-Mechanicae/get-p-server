package es.princip.getp.domain.people.command.application;

import es.princip.getp.domain.client.command.domain.Client;
import es.princip.getp.domain.client.command.domain.ClientRepository;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.people.exception.PeopleErrorCode;
import es.princip.getp.infra.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeopleLikeService {

    private final PeopleRepository peopleRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public void like(Long memberId, Long peopleId) {
        if (!peopleRepository.existsById(peopleId)) {
            throw new BusinessLogicException(PeopleErrorCode.NOT_FOUND);
        }
        Client client = clientRepository.findByMemberId(memberId).orElseThrow();
        client.likePeople(peopleId);
    }

    @Transactional
    public void unlike(Long memberId, Long peopleId) {
        if (!peopleRepository.existsById(peopleId)) {
            throw new BusinessLogicException(PeopleErrorCode.NOT_FOUND);
        }
        Client client = clientRepository.findByMemberId(memberId).orElseThrow();
        client.unlikePeople(peopleId);
    }
}
