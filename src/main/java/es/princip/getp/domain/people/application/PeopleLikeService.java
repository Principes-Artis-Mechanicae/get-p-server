package es.princip.getp.domain.people.application;

import es.princip.getp.domain.client.application.ClientService;
import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleLike;
import es.princip.getp.domain.people.domain.PeopleLikeRepository;
import es.princip.getp.domain.people.exception.PeopleLikeErrorCode;
import es.princip.getp.infra.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PeopleLikeService {
    private final PeopleService peopleService;
    private final ClientService clientService;
    private final PeopleLikeRepository peopleLikeRepository;

    private PeopleLike get(Optional<PeopleLike> peopleLike) {
        return peopleLike.orElseThrow(() -> new BusinessLogicException(PeopleLikeErrorCode.NEVER_LIKED));
    }

    public PeopleLike getByMemberIdAndPeopleId(Long memberId, Long peopleId) {
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
        PeopleLike peopleLike = getByMemberIdAndPeopleId(memberId, peopleId);
        peopleLikeRepository.delete(peopleLike);
    }
}