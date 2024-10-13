package es.princip.getp.persistence.adapter.like.people;

import es.princip.getp.application.like.people.port.out.CheckPeopleLikePort;
import es.princip.getp.application.like.people.port.out.DeletePeopleLikePort;
import es.princip.getp.application.like.people.port.out.LoadPeopleLikePort;
import es.princip.getp.application.like.people.port.out.SavePeopleLikePort;
import es.princip.getp.domain.like.people.model.PeopleLike;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.persistence.adapter.like.exception.NotFoundLikeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class PeopleLikePersistenceAdapter implements
    CheckPeopleLikePort,
    SavePeopleLikePort,
    DeletePeopleLikePort,
    LoadPeopleLikePort {
    
    private final PeopleLikeJpaRepository repository;
    private final PeopleLikePersistenceMapper mapper;
    
    @Override
    public void delete(final PeopleLike like) {
        final PeopleLikeJpaEntity entity = mapper.mapToJpa(like);
        repository.deleteById(entity.getId());
    }

    @Override
    public void save(final PeopleLike like) {
        final PeopleLikeJpaEntity entity = mapper.mapToJpa(like);
        repository.save(entity);
    }

    @Override
    public PeopleLike loadBy(final MemberId memberId, final PeopleId peopleId) {
        final PeopleLikeJpaEntity entity = repository.findByMemberIdAndPeopleId(
                memberId.getValue(),
                peopleId.getValue()
            )
            .orElseThrow(NotFoundLikeException::new);
        return mapper.mapToDomain(entity);
    }

    @Override
    public boolean existsBy(final MemberId memberId, final PeopleId peopleId) {
        return repository.existsByMemberIdAndPeopleId(
            memberId.getValue(),
            peopleId.getValue()
        );
    }

    @Override
    public Boolean existsBy(final Member member, final PeopleId peopleId) {
        if (member == null || member.isPeople()) {
            return null;
        }
        return existsBy(member.getId(), peopleId);
    }
}