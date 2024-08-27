package es.princip.getp.fixture.client;

import es.princip.getp.domain.client.model.Client;

import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.fixture.member.EmailFixture.email;

public class ClientFixture {

    public static Client client(final Long memberId) {
        return Client.builder()
            .memberId(memberId)
            .email(email())
            .address(AddressFixture.address())
            .bankAccount(BankAccountFixture.bankAccount())
            .build();
    }

    public static List<Client> clientList(final int size, final Long memberIdBias) {
        return LongStream.range(0, size)
            .mapToObj(i -> client(memberIdBias + i))
            .toList();
    }
}
