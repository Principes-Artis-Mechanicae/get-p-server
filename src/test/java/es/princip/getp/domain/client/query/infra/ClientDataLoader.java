package es.princip.getp.domain.client.query.infra;

import es.princip.getp.common.util.DataLoader;
import es.princip.getp.domain.client.command.domain.Client;
import es.princip.getp.domain.member.command.domain.model.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static es.princip.getp.domain.client.fixture.ClientFixture.clientList;
import static es.princip.getp.domain.member.command.domain.model.MemberType.ROLE_CLIENT;
import static es.princip.getp.domain.member.fixture.MemberFixture.memberList;

@RequiredArgsConstructor
public class ClientDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Override
    public void load(final int size) {
        final List<Member> memberList = memberList(size, 1, ROLE_CLIENT);
        memberList.forEach(entityManager::persist);

        final Long memberIdBias = memberList.stream()
            .findFirst()
            .map(Member::getMemberId)
            .orElse(1L);

        final List<Client> clientList = new ArrayList<>(clientList(size, memberIdBias));
        clientList.forEach(entityManager::persist);;
    }

    @Override
    public void teardown() {
        entityManager.createQuery("DELETE FROM Client").executeUpdate();
        entityManager.createQuery("DELETE FROM Member").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE member AUTO_INCREMENT = 1")
            .executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE client AUTO_INCREMENT = 1")
            .executeUpdate();
    }
}
