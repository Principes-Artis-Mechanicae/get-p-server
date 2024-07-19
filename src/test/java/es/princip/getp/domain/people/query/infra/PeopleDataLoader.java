package es.princip.getp.domain.people.query.infra;

import es.princip.getp.domain.member.command.domain.model.Member;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleProfile;
import es.princip.getp.domain.people.command.domain.PeopleType;
import es.princip.getp.infra.DataLoader;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static es.princip.getp.domain.member.command.domain.model.MemberType.ROLE_PEOPLE;
import static es.princip.getp.domain.member.fixture.MemberFixture.memberList;
import static es.princip.getp.domain.people.fixture.PeopleFixture.peopleList;
import static es.princip.getp.domain.people.fixture.PeopleProfileFixture.peopleProfileList;

@RequiredArgsConstructor
public class PeopleDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Transactional
    @Override
    public void load(final int size) {
        loadMember(size);
        loadPeople(size);
        loadPeopleProfile(size);
    }

    private void loadMember(final int size) {
        List<Member> memberList = memberList(size, ROLE_PEOPLE);

        memberList.forEach(entityManager::persist);
    }

    private void loadPeople(final int size) {
        final long memberIdBias = 1;
        final int half = size / 2;

        List<People> peopleList = new ArrayList<>(peopleList(half, memberIdBias, PeopleType.INDIVIDUAL));
        peopleList.addAll(peopleList((size % 2) == 0 ? half : half + 1, size + memberIdBias, PeopleType.TEAM));

        peopleList.forEach(entityManager::persist);
    }

    private void loadPeopleProfile(final int size) {
        final long peopleIdIdBias = 1;

        List<PeopleProfile> peopleProfileList = peopleProfileList(size, peopleIdIdBias);

        peopleProfileList.forEach(entityManager::persist);
    }
}
