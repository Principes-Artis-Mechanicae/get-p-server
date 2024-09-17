package es.princip.getp.persistence.adapter.people;

import es.princip.getp.application.people.port.out.*;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.persistence.adapter.people.mapper.PeoplePersistenceMapper;
import es.princip.getp.persistence.adapter.people.model.PeopleJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class PeoplePersistenceAdapter implements
        SavePeoplePort,
        LoadPeoplePort,
        CheckPeoplePort,
        UpdatePeoplePort,
        DeletePeoplePort {

    private final PeoplePersistenceMapper mapper;

    private final PeopleJpaRepository peopleJpaRepository;

    @Override
    public boolean existsBy(final MemberId memberId) {
        return peopleJpaRepository.existsByMemberId(memberId.getValue());
    }

    @Override
    public People loadBy(final MemberId memberId) {
        final PeopleJpaEntity peopleJpaEntity = peopleJpaRepository.findByMemberId(memberId.getValue())
            .orElseThrow(NotFoundPeopleException::new);
        return mapper.mapToDomain(peopleJpaEntity);
    }

    @Override
    public People loadByPeopleId(final Long peopleId) {
        final PeopleJpaEntity peopleJpaEntity = peopleJpaRepository.findById(peopleId)
            .orElseThrow(NotFoundPeopleException::new);
        return mapper.mapToDomain(peopleJpaEntity);
    }

    @Override
    public Long save(final People people) {
        return peopleJpaRepository.save(mapper.mapToJpa(people)).getId();
    }

    @Override
    public void update(final People people) {
        if (!peopleJpaRepository.existsById(people.getId())) {
            throw new NotFoundPeopleException();
        }
        peopleJpaRepository.save(mapper.mapToJpa(people));
    }

    @Override
    public void delete(final Long peopleId) {
        if (!peopleJpaRepository.existsById(peopleId)) {
            throw new NotFoundPeopleException();
        }
        peopleJpaRepository.deleteById(peopleId);
    }
}
