package es.princip.getp.domain.client.query.infra;

import es.princip.getp.common.util.DataLoader;
import es.princip.getp.domain.client.command.domain.Client;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.persistence.adapter.member.MemberJpaEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.domain.client.fixture.ClientFixture.clientList;
import static es.princip.getp.domain.member.fixture.PasswordFixture.PASSWORD;

@RequiredArgsConstructor
public class ClientDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Override
    public void load(final int size) {
        final List<MemberJpaEntity> memberList = LongStream.range(1, 1 + size)
            .mapToObj(i -> MemberJpaEntity.builder()
                .email("test" + i + "@example.com")
                .password(PASSWORD)
                .memberType(MemberType.ROLE_CLIENT)
                .build())
            .toList();
        memberList.forEach(entityManager::persist);

        final Long memberIdBias = memberList.stream()
            .findFirst()
            .map(MemberJpaEntity::getMemberId)
            .orElse(1L);

        final List<Client> clientList = new ArrayList<>(clientList(size, memberIdBias));
        clientList.forEach(entityManager::persist);;
    }

    @Override
    public void teardown() {
        entityManager.createQuery("DELETE FROM Client").executeUpdate();
        entityManager.createQuery("DELETE FROM MemberJpaEntity").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE member AUTO_INCREMENT = 1")
            .executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE client AUTO_INCREMENT = 1")
            .executeUpdate();
    }
}
