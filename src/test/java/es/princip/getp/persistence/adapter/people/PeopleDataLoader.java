package es.princip.getp.persistence.adapter.people;

import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.persistence.adapter.people.mapper.PeoplePersistenceMapper;
import es.princip.getp.persistence.adapter.people.model.PeopleJpaEntity;
import es.princip.getp.persistence.support.DataLoader;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.fixture.people.PeopleFixture.people;

@RequiredArgsConstructor
public class PeopleDataLoader implements DataLoader {

    private final PeoplePersistenceMapper mapper;
    private final EntityManager entityManager;

    @Override
    public void load(final int size) {
        final long memberIdBias = 1L;
        final int individualSize = size / 2;
        final int teamSize = (size % 2) == 0 ? size / 2 : (size / 2) + 1;

        final List<PeopleJpaEntity> individualList = LongStream.range(0, individualSize)
            .mapToObj(i -> people(new MemberId(memberIdBias + i)))
            .toList()
            .stream()
            .map(mapper::mapToJpa)
            .toList();
        final List<PeopleJpaEntity> teamList = LongStream.range(0, teamSize)
            .mapToObj(i -> people(new MemberId(size + memberIdBias + i)))
            .toList()
            .stream()
            .map(mapper::mapToJpa)
            .toList();

        individualList.forEach(entityManager::persist);
        teamList.forEach(entityManager::persist);
    }

    @Override
    public void teardown() {
        entityManager.createQuery("DELETE FROM PeopleJpaEntity").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE people AUTO_INCREMENT = 1").executeUpdate();
    }
}
