package es.princip.getp.persistence.adapter.member;

import es.princip.getp.persistence.support.DataLoader;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.fixture.member.PasswordFixture.PASSWORD;

@RequiredArgsConstructor
public class MemberDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Override
    public void load(final int size) {
        final List<MemberJpaEntity> memberList = LongStream.range(1, 1 + size)
            .mapToObj(i -> MemberJpaEntity.builder()
                .email("test" + i + "@example.com")
                .password(PASSWORD)
                .build())
            .toList();
        memberList.forEach(entityManager::persist);
    }

    @Override
    public void teardown() {
        entityManager.createQuery("DELETE FROM MemberJpaEntity").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE member AUTO_INCREMENT = 1").executeUpdate();
    }
}
