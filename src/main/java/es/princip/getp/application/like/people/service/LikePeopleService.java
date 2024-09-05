package es.princip.getp.application.like.people.service;

import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.application.like.exception.AlreadyLikedException;
import es.princip.getp.application.like.people.port.out.CheckPeopleLikePort;
import es.princip.getp.application.like.people.port.out.SavePeopleLikePort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.like.people.model.PeopleLike;
import es.princip.getp.domain.people.model.People;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class LikePeopleService implements es.princip.getp.application.like.people.port.in.LikePeopleUseCase {

    private final LoadClientPort loadClientPort;
    private final LoadPeoplePort loadPeoplePort;
    private final CheckPeopleLikePort checkPeopleLikePort;
    private final SavePeopleLikePort savePeopleLikePort;

    @Transactional
    public void like(final Long memberId, final Long peopleId) {
        final Client client = loadClientPort.loadBy(memberId);
        // TODO: 조회 성능 개선 필요
        final People people = loadPeoplePort.loadByPeopleId(peopleId);
        checkAlreadyLiked(client.getClientId(), peopleId);
        final PeopleLike peopleLike = buildPeopleLike(client.getClientId(), peopleId);
        savePeopleLikePort.save(peopleLike);
    }

    private void checkAlreadyLiked(final Long clientId, final Long peopleId) {
        if (checkPeopleLikePort.existsBy(clientId, peopleId)) {
            throw new AlreadyLikedException();
        }
    }

    private PeopleLike buildPeopleLike(final Long clientId, final Long peopleId) {
        return PeopleLike.builder()
            .clientId(clientId)
            .peopleId(peopleId)
            .build();
    }
}