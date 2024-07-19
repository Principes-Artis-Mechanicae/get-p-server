package es.princip.getp.domain.project.query.infra;

import es.princip.getp.domain.client.command.domain.Client;
import es.princip.getp.domain.member.command.domain.model.Member;
import es.princip.getp.domain.project.command.domain.Project;
import es.princip.getp.infra.DataLoader;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static es.princip.getp.domain.client.fixture.ClientFixture.clientList;
import static es.princip.getp.domain.member.command.domain.model.MemberType.ROLE_PEOPLE;
import static es.princip.getp.domain.member.fixture.MemberFixture.memberList;
import static es.princip.getp.domain.project.fixture.ProjectFixture.projectList;

@RequiredArgsConstructor
public class ProjectDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Transactional
    @Override
    public void load(final int size) {
        loadMember(size);
        loadClient(size);
        loadProject(size);
    }

    private void loadMember(final int size) {
        List<Member> memberList = memberList(size, ROLE_PEOPLE);

        memberList.forEach(entityManager::persist);
    }

    private void loadClient(final int size) {
        final long memberIdBias = 1;

        List<Client> clientList = clientList(size, memberIdBias);

        clientList.forEach(entityManager::persist);
    }

    private void loadProject(final int size) {
        final long clientIdBias = 1;

        List<Project> projectList = projectList(size, clientIdBias);

        projectList.forEach(entityManager::persist);
    }
}
