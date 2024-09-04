package es.princip.getp.persistence.adapter.like.command.people;

import es.princip.getp.application.like.port.out.people.CheckPeopleLikePort;
import es.princip.getp.application.like.port.out.people.LoadPeopleLikePort;
import es.princip.getp.application.like.port.out.people.PeopleLikePort;
import es.princip.getp.application.like.port.out.people.PeopleUnlikePort;
import es.princip.getp.domain.like.model.people.PeopleLike;
import es.princip.getp.persistence.adapter.like.command.PeopleLikePersistenceMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class PeopleLikePersistenceAdapter implements 
        CheckPeopleLikePort,
        PeopleLikePort,
        PeopleUnlikePort,
        LoadPeopleLikePort {
    
    private final PeopleLikeJpaRepository repository;
    private final PeopleLikePersistenceMapper mapper;
    
    @Override
    public void unlike(PeopleLike like) {
        PeopleLikeJpaEntity entity = mapper.mapToJpa(like);
        repository.deleteById(entity.getId());
    }

    @Override
    public boolean existsByClientIdAndPeopleId(Long clientId, Long peopleId) {
        return repository.existsByClientIdAndPeopleId(clientId, peopleId);
    }

    @Override
    public void like(PeopleLike like) {
        PeopleLikeJpaEntity entity = mapper.mapToJpa(like);
        repository.save(entity);
    }

    @Override
    public PeopleLike findByClientIdAndPeopleId(Long clientId, Long peopleId) {
        PeopleLikeJpaEntity entity = repository.findByClientIdAndPeopleId(clientId, peopleId);
        return mapper.mapToDomain(entity);
    }

}
