package es.princip.getp.domain.people.query.infra;

import es.princip.getp.domain.member.command.domain.model.Member;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleType;
import es.princip.getp.infra.DataLoader;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static es.princip.getp.domain.member.command.domain.model.MemberType.ROLE_PEOPLE;
import static es.princip.getp.domain.member.fixture.MemberFixture.memberList;
import static es.princip.getp.domain.people.fixture.PeopleFixture.peopleList;

@RequiredArgsConstructor
public class PeopleDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Override
    public void load(final int size) {
        final List<Member> memberList = memberList(size, 1, ROLE_PEOPLE);
        memberList.forEach(entityManager::persist);

        final Long memberIdBias = memberList.stream()
            .findFirst()
            .map(Member::getMemberId)
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
        entityManager.createQuery("DELETE FROM People").executeUpdate();
        entityManager.createQuery("DELETE FROM Member").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE member AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE people AUTO_INCREMENT = 1").executeUpdate();
    }
}
