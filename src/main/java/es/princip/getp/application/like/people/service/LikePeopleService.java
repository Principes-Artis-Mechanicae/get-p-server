package es.princip.getp.application.like.people.service;

import es.princip.getp.application.like.exception.AlreadyLikedException;
import es.princip.getp.application.like.people.port.in.LikePeopleUseCase;
import es.princip.getp.application.like.people.port.out.CheckPeopleLikePort;
import es.princip.getp.application.like.people.port.out.SavePeopleLikePort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.domain.like.people.model.PeopleLike;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class LikePeopleService implements LikePeopleUseCase {

    private final LoadPeoplePort loadPeoplePort;
    private final CheckPeopleLikePort checkPeopleLikePort;
    private final SavePeopleLikePort savePeopleLikePort;

    @Transactional
    public void like(final MemberId memberId, final PeopleId peopleId) {
        // TODO: 조회 성능 개선 필요
        final People people = loadPeoplePort.loadBy(peopleId);
        checkAlreadyLiked(memberId, peopleId);
        final PeopleLike peopleLike = buildPeopleLike(memberId, peopleId);
        savePeopleLikePort.save(peopleLike);
    }

    private void checkAlreadyLiked(final MemberId memberId, final PeopleId peopleId) {
        if (checkPeopleLikePort.existsBy(memberId, peopleId)) {
            throw new AlreadyLikedException();
        }
    }

    private PeopleLike buildPeopleLike(final MemberId memberId, final PeopleId peopleId) {
        return PeopleLike.builder()
            .memberId(memberId)
            .peopleId(peopleId)
            .build();
    }
}