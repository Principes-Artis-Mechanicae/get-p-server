package es.princip.getp.application.like;

import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.application.like.exception.AlreadyLikedException;
import es.princip.getp.application.like.exception.NeverLikedException;
import es.princip.getp.application.like.port.out.people.CheckPeopleLikePort;
import es.princip.getp.application.like.port.out.people.LoadPeopleLikePort;
import es.princip.getp.application.like.port.out.people.PeopleLikePort;
import es.princip.getp.application.like.port.out.people.PeopleUnlikePort;
import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.like.model.people.PeopleLike;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.people.exception.NotFoundPeopleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PeopleLikeService {

    private final LoadPeopleLikePort loadPeopleLikePort;

    private final CheckPeopleLikePort checkPeopleLikePort;

    private final PeopleLikePort peopleLikePort;

    private final PeopleUnlikePort peopleUnlikePort;

    private final LoadClientPort loadClientPort;

    private final PeopleRepository peopleRepository;

    /**
     * 피플에게 좋아요를 누른다.
     *
     * @param memberId 좋아요를 요청한 의뢰자의 회원 ID
     * @param peopleId 좋아요 대상 피플 ID
     * @throws NotFoundPeopleException 해당 피플이 존재하지 않는 경우
     */
    @Transactional
    public void like(final Long memberId, final Long peopleId) {
        final Client client = loadClientPort.loadBy(memberId);
        final People people = peopleRepository.findById(peopleId)
            .orElseThrow(NotFoundPeopleException::new);

        checkAlreadyLiked(client.getClientId(), peopleId);
        PeopleLike peopleLike = buildPeopleLike(client.getClientId(), peopleId);
        
        peopleLikePort.like(peopleLike);
    }

    /**
     * 피플에게 눌렀던 좋아요를 취소한다.
     *
     * @param memberId 좋아요 취소를 요청한 의뢰자의 회원 ID
     * @param peopleId 좋아요 취소 대상 피플 ID
     * @throws NotFoundPeopleException 해당 피플이 존재하지 않는 경우
     */
    @Transactional
    public void unlike(final Long memberId, final Long peopleId) {
        final Client client = loadClientPort.loadBy(memberId);
        final People people = peopleRepository.findById(peopleId)
            .orElseThrow(NotFoundPeopleException::new);

        
        PeopleLike peopleLike = loadPeopleLikePort.findByClientIdAndPeopleId(client.getClientId(), peopleId);
        checkNeverLiked(client.getClientId(), peopleId);
        
        peopleUnlikePort.unlike(peopleLike);
    }

    private void checkAlreadyLiked(final Long clientId, final Long peopleId) {
        if (checkPeopleLikePort.existsByClientIdAndPeopleId(clientId, peopleId)) {
            throw new AlreadyLikedException();
        }
    }

    private void checkNeverLiked(final Long clientId, final Long peopleId) {
        if (!checkPeopleLikePort.existsByClientIdAndPeopleId(clientId, peopleId)) {
            throw new NeverLikedException();
        } 
    }

    private PeopleLike buildPeopleLike(final Long clientId, final Long peopleId) {
        return PeopleLike.builder()
            .clientId(clientId)
            .peopleId(peopleId)
            .build();
    }
}