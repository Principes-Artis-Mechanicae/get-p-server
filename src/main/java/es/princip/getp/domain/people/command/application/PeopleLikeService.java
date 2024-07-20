package es.princip.getp.domain.people.command.application;

import es.princip.getp.domain.client.command.domain.Client;
import es.princip.getp.domain.client.command.domain.ClientRepository;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeopleLikeService {

    private final PeopleRepository peopleRepository;
    private final ClientRepository clientRepository;

    /**
     * 피플을 좋아요 한다.
     *
     * @param memberId 좋아요를 요청한 회원 ID
     * @param peopleId 좋아요 대상 피플 ID
     * @throws EntityNotFoundException 해당 피플이 존재하지 않는 경우
     */
    @Transactional
    public void like(final Long memberId, final Long peopleId) {
        if (!peopleRepository.existsById(peopleId)) {
            throw new EntityNotFoundException("해당 피플이 존재하지 않습니다.");
        }
        final Client client = clientRepository.findByMemberId(memberId).orElseThrow();
        client.likePeople(peopleId);
    }

    /**
     * 피플 좋아요를 취소한다.
     *
     * @param memberId 좋아요 취소를 요청한 회원 ID
     * @param peopleId 좋아요 취소 대상 피플 ID
     * @throws EntityNotFoundException 해당 피플이 존재하지 않는 경우
     */
    @Transactional
    public void unlike(final Long memberId, final Long peopleId) {
        if (!peopleRepository.existsById(peopleId)) {
            throw new EntityNotFoundException("해당 피플이 존재하지 않습니다.");
        }
        final Client client = clientRepository.findByMemberId(memberId).orElseThrow();
        client.unlikePeople(peopleId);
    }
}
