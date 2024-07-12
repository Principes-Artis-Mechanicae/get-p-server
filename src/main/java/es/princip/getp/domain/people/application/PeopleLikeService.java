package es.princip.getp.domain.people.application;

import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.client.domain.ClientRepository;
import es.princip.getp.domain.people.domain.PeopleRepository;
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
            throw new IllegalArgumentException("People does not exist");
        }
        Client client = clientRepository.findByMemberId(memberId).orElseThrow();
        client.likePeople(peopleId);
    }

    @Transactional
    public void unlike(Long memberId, Long peopleId) {
        if (!peopleRepository.existsById(peopleId)) {
            throw new IllegalArgumentException("People does not exist");
        }
        Client client = clientRepository.findByMemberId(memberId).orElseThrow();
        client.unlikePeople(peopleId);
    }
}
