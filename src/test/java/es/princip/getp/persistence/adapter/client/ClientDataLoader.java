package es.princip.getp.persistence.adapter.client;

import es.princip.getp.persistence.support.DataLoader;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.fixture.client.AddressFixture.*;
import static es.princip.getp.fixture.client.BankAccountFixture.*;

@RequiredArgsConstructor
public class ClientDataLoader implements DataLoader {

    private final EntityManager entityManager;

    @Override
    public void load(final int size) {
        final List<ClientJpaEntity> clientList = LongStream.rangeClosed(1, size)
            .mapToObj(i -> ClientJpaEntity.builder()
                .memberId(i)
                .email("test" + i + "@example.com")
                .address(new AddressJpaVO(ZIPCODE, STREET, DETAIL))
                .bankAccount(new BankAccountJpaVO(BANK, ACCOUNT_NUMBER, ACCOUNT_HOLDER))
                .build())
            .toList();
        clientList.forEach(entityManager::persist);
    }

    @Override
    public void teardown() {
        entityManager.createQuery("DELETE FROM ClientJpaEntity").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE client AUTO_INCREMENT = 1")
            .executeUpdate();
    }
}
