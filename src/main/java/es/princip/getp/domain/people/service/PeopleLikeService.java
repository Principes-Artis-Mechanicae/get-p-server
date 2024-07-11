package es.princip.getp.domain.people.service;

import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.client.service.ClientService;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleLike;
import es.princip.getp.domain.people.exception.PeopleLikeErrorCode;
import es.princip.getp.domain.people.repository.PeopleLikeRepository;
import es.princip.getp.infra.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PeopleLikeService {
    private final PeopleService peopleService;
    private final ClientService clientService;
    private final PeopleLikeRepository peopleLikeRepository;

    private void checkPeopleIsAlreadyLiked(Long clientId, Long peopleId) {
        if (peopleLikeRepository.existsByClient_ClientIdAndPeople_PeopleId(clientId, peopleId)) {
            throw new BusinessLogicException(PeopleLikeErrorCode.ALREADY_LIKED);
        }
    }

    @Transactional
    public void like(Long memberId, Long peopleId) {
        Client client = clientService.getByMemberId(memberId);
        People people = peopleService.getByPeopleId(peopleId);
        checkPeopleIsAlreadyLiked(client.getClientId(), peopleId);
        peopleLikeRepository.save(
            PeopleLike.builder()
                .client(client)
                .people(people)
                .build()
        );
    }
}
