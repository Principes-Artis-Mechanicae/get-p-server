package es.princip.getp.persistence.adapter.like.people;

import es.princip.getp.application.like.people.port.out.CheckPeopleLikePort;
import es.princip.getp.application.like.people.port.out.DeletePeopleLikePort;
import es.princip.getp.application.like.people.port.out.LoadPeopleLikePort;
import es.princip.getp.application.like.people.port.out.SavePeopleLikePort;
import es.princip.getp.domain.like.people.model.PeopleLike;
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
    public boolean existsBy(final Long clientId, final Long peopleId) {
        return repository.existsByClientIdAndPeopleId(clientId, peopleId);
    }

    @Override
    public void save(PeopleLike like) {
        final PeopleLikeJpaEntity entity = mapper.mapToJpa(like);
        repository.save(entity);
    }

    @Override
    public PeopleLike loadBy(final Long clientId, final Long peopleId) {
        final PeopleLikeJpaEntity entity = repository.findByClientIdAndPeopleId(clientId, peopleId);
        return mapper.mapToDomain(entity);
    }
}
