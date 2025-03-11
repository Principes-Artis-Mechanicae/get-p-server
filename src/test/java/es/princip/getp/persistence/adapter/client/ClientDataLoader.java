package es.princip.getp.persistence.adapter.client;

import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.persistence.support.DataLoader;
import es.princip.getp.persistence.adapter.member.MemberJpaEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.fixture.client.AddressFixture.*;
import static es.princip.getp.fixture.client.BankAccountFixture.*;
import static es.princip.getp.fixture.member.PasswordFixture.PASSWORD;

@RequiredArgsConstructor
public class ClientDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Override
    public void load(final int size) {
        final List<MemberJpaEntity> memberList = LongStream.rangeClosed(1, size)
            .mapToObj(i -> MemberJpaEntity.builder()
                .email("test" + i + "@example.com")
                .password(PASSWORD)
                .memberType(MemberType.ROLE_CLIENT)
                .build())
            .toList();
        final List<ClientJpaEntity> clientList = LongStream.rangeClosed(1, size)
            .mapToObj(i -> ClientJpaEntity.builder()
                .memberId(i)
                .email("test" + i + "@example.com")
                .address(new AddressJpaVO(ZIPCODE, STREET, DETAIL))
                .bankAccount(new BankAccountJpaVO(BANK, ACCOUNT_NUMBER, ACCOUNT_HOLDER))
                .build())
            .toList();

        memberList.forEach(entityManager::persist);
        clientList.forEach(entityManager::persist);
    }

    @Override
    public void teardown() {
        entityManager.createQuery("DELETE FROM ClientJpaEntity").executeUpdate();
        entityManager.createQuery("DELETE FROM MemberJpaEntity").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE member AUTO_INCREMENT = 1")
            .executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE client AUTO_INCREMENT = 1")
            .executeUpdate();
    }
}
