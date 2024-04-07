package es.princip.getp.domain.people.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.princip.getp.domain.client.domain.entity.Client;
import es.princip.getp.domain.client.service.ClientService;
import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.domain.entity.PeopleLike;
import es.princip.getp.domain.people.exception.PeopleLikeErrorCode;
import es.princip.getp.domain.people.repository.PeopleLikeRepository;
import es.princip.getp.global.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PeopleLikeService {
    private final PeopleService peopleService;
    private final ClientService clientService;
    private final PeopleLikeRepository peopleLikeRepository;

    private PeopleLike get(Optional<PeopleLike> peopleLike) {
        return peopleLike.orElseThrow(() -> new BusinessLogicException(PeopleLikeErrorCode.PEOPLE_LIKE_NOT_FOUND));
    }

    private PeopleLike get(Long memberId, Long peopleId) {
        return get(peopleLikeRepository.findByPeople_PeopleIdAndClient_ClientId(memberId, peopleId));
    }

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

    @Transactional
    public void unlike(Long memberId, Long peopleId) {
        PeopleLike peopleLike = get(memberId, peopleId);
        peopleLikeRepository.delete(peopleLike);
    }
}
