package es.princip.getp.domain.like.command.application;

import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.like.command.domain.people.PeopleLiker;
import es.princip.getp.domain.like.command.domain.people.PeopleUnliker;
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

    private final PeopleLiker peopleLiker;
    private final PeopleUnliker peopleUnliker;

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

        peopleLiker.like(client, people);
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

        peopleUnliker.unlike(client, people);
    }
}
