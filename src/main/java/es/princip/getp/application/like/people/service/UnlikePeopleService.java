package es.princip.getp.application.like.people.service;

import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.application.like.exception.NeverLikedException;
import es.princip.getp.application.like.people.port.in.UnlikePeopleUseCase;
import es.princip.getp.application.like.people.port.out.CheckPeopleLikePort;
import es.princip.getp.application.like.people.port.out.DeletePeopleLikePort;
import es.princip.getp.application.like.people.port.out.LoadPeopleLikePort;
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
class UnlikePeopleService implements UnlikePeopleUseCase {

    private final LoadClientPort loadClientPort;
    private final LoadPeoplePort loadPeoplePort;
    private final LoadPeopleLikePort loadPeopleLikePort;
    private final CheckPeopleLikePort checkPeopleLikePort;
    private final DeletePeopleLikePort deletePeopleLikePort;

    @Transactional
    public void unlike(final Long memberId, final Long peopleId) {
        final Client client = loadClientPort.loadBy(memberId);
        // TODO: 조회 성능 개선 필요
        final People people = loadPeoplePort.loadByPeopleId(peopleId);
        final PeopleLike peopleLike = loadPeopleLikePort.loadBy(client.getClientId(), peopleId);
        checkNeverLiked(client.getClientId(), peopleId);
        deletePeopleLikePort.delete(peopleLike);
    }

    private void checkNeverLiked(final Long clientId, final Long peopleId) {
        if (!checkPeopleLikePort.existsBy(clientId, peopleId)) {
            throw new NeverLikedException();
        } 
    }
}