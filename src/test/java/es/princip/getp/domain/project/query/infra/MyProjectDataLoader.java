package es.princip.getp.domain.project.query.infra;

import es.princip.getp.domain.client.command.domain.Client;
import es.princip.getp.domain.member.command.domain.model.Member;
import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.domain.project.command.domain.Project;
import es.princip.getp.infra.DataLoader;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static es.princip.getp.domain.client.fixture.ClientFixture.client;
import static es.princip.getp.domain.member.fixture.MemberFixture.member;
import static es.princip.getp.domain.project.fixture.ProjectFixture.project;

@RequiredArgsConstructor
public class MyProjectDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Transactional
    @Override
    public void load(final int size) {
        final Long memberId = loadMember();
        final Long clientId = loadClient(memberId);
        loadProject(size, clientId);
    }

    private Long loadMember() {
        final Member member = member(MemberType.ROLE_CLIENT);
        entityManager.persist(member);
        return member.getMemberId();
    }

    private Long loadClient(final Long memberId) {
        final Client client = client(memberId);
        entityManager.persist(client);
        return client.getClientId();
    }

    private void loadProject(final int size, final Long clientId) {
        final List<Project> projectList = IntStream.rangeClosed(1, size)
            .mapToObj(i -> project(clientId))
            .toList();

        projectList.forEach(entityManager::persist);
    }
}
