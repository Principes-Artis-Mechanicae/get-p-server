package es.princip.getp.domain.project.query.infra;

import es.princip.getp.domain.member.command.domain.model.Member;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleType;
import es.princip.getp.domain.project.command.domain.ProjectApplication;
import es.princip.getp.infra.DataLoader;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.domain.member.command.domain.model.MemberType.ROLE_PEOPLE;
import static es.princip.getp.domain.project.fixture.ProjectApplicationFixture.projectApplication;
import static es.princip.getp.domain.member.fixture.MemberFixture.memberList;
import static es.princip.getp.domain.people.fixture.PeopleFixture.peopleList;

@RequiredArgsConstructor
public class ProjectApplicationDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Transactional
    @Override
    public void load(final int size) {
        loadMember(size);
        loadPeople(size);
        loadProjectApplication(size);
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

    private void loadProjectApplication(final int size) {
        final List<ProjectApplication> projectApplicationList = new ArrayList<>();
        LongStream.rangeClosed(1, size).forEach(projectId ->
            LongStream.rangeClosed(1, size).forEach(peopleId ->
                projectApplicationList.add(projectApplication(peopleId, projectId))
            )
        );

        projectApplicationList.forEach(entityManager::persist);
    }
}
