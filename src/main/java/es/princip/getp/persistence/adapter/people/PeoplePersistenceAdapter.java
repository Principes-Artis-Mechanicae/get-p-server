package es.princip.getp.persistence.adapter.people;

import es.princip.getp.application.people.port.out.*;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.persistence.adapter.people.mapper.PeoplePersistenceMapper;
import es.princip.getp.persistence.adapter.people.model.PeopleJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
class PeoplePersistenceAdapter implements
        SavePeoplePort,
        LoadPeoplePort,
        CheckPeoplePort,
        UpdatePeoplePort,
        DeletePeoplePort {

    private final PeoplePersistenceMapper mapper;
    private final PeopleJpaRepository repository;

    @Override
    public boolean existsBy(final MemberId memberId) {
        return repository.existsByMemberId(memberId.getValue());
    }

    @Override
    public People loadBy(final MemberId memberId) {
        final PeopleJpaEntity peopleJpaEntity = repository.findByMemberId(memberId.getValue())
            .orElseThrow(NotFoundPeopleException::new);
        return mapper.mapToDomain(peopleJpaEntity);
    }

    @Override
    public People loadBy(final PeopleId peopleId) {
        final PeopleJpaEntity peopleJpaEntity = repository.findById(peopleId.getValue())
            .orElseThrow(NotFoundPeopleException::new);
        return mapper.mapToDomain(peopleJpaEntity);
    }

    @Override
    public Set<People> loadBy(final Set<PeopleId> peopleIds) {
        final Set<Long> ids = peopleIds.stream()
            .map(PeopleId::getValue)
            .collect(Collectors.toSet());
        final Set<PeopleJpaEntity> entities = new HashSet<>(repository.findAllById(ids));
        final Set<Long> founded = entities.stream()
            .map(PeopleJpaEntity::getId)
            .collect(Collectors.toSet());
        ids.removeAll(founded);
        if (!ids.isEmpty()) {
            throw NotFoundPeopleException.from(ids);
        }
        return entities.stream()
            .map(mapper::mapToDomain)
            .collect(Collectors.toSet());
    }

    @Override
    public PeopleId save(final People people) {
        final PeopleJpaEntity peopleJpaEntity = mapper.mapToJpa(people);
        final Long id = repository.save(peopleJpaEntity).getId();
        return new PeopleId(id);
    }

    @Override
    public void update(final People people) {
        final Long id = people.getId().getValue();
        if (!repository.existsById(id)) {
            throw new NotFoundPeopleException();
        }
        repository.save(mapper.mapToJpa(people));
    }

    @Override
    public void delete(final PeopleId peopleId) {
        final Long id = peopleId.getValue();
        if (!repository.existsById(id)) {
            throw new NotFoundPeopleException();
        }
        repository.deleteById(id);
    }
}
