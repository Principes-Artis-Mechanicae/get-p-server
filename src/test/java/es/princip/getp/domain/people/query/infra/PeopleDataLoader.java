package es.princip.getp.domain.people.query.infra;

import es.princip.getp.common.util.DataLoader;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleType;
import es.princip.getp.persistence.adapter.member.MemberJpaEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.domain.member.model.MemberType.ROLE_PEOPLE;
import static es.princip.getp.fixture.member.PasswordFixture.PASSWORD;
import static es.princip.getp.fixture.people.PeopleFixture.peopleList;

@RequiredArgsConstructor
public class PeopleDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Override
    public void load(final int size) {
        final List<MemberJpaEntity> memberList = LongStream.range(1, 1 + size)
            .mapToObj(i -> MemberJpaEntity.builder()
                .email("test" + i + "@example.com")
                .password(PASSWORD)
                .memberType(ROLE_PEOPLE)
                .build())
            .toList();
        memberList.forEach(entityManager::persist);

        final Long memberIdBias = memberList.stream()
            .findFirst()
            .map(MemberJpaEntity::getMemberId)
            .orElse(1L);
        final int individualSize = size / 2;
        final int teamSize = (size % 2) == 0 ? size / 2 : (size / 2) + 1;

        final List<People> individualList = peopleList(individualSize, memberIdBias, PeopleType.INDIVIDUAL);
        final List<People> teamList = peopleList(teamSize, size + memberIdBias, PeopleType.TEAM);
        individualList.forEach(entityManager::persist);
        teamList.forEach(entityManager::persist);
    }

    @Override
    public void teardown() {
        entityManager.createQuery("DELETE FROM PeopleJpaEntity").executeUpdate();
        entityManager.createQuery("DELETE FROM MemberJpaEntity").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE member AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE people AUTO_INCREMENT = 1").executeUpdate();
    }
}
