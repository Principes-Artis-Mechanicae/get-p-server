package es.princip.getp.domain.client.fixture;

import es.princip.getp.domain.client.command.domain.Client;

import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.domain.client.fixture.AddressFixture.address;
import static es.princip.getp.domain.client.fixture.BankAccountFixture.bankAccount;
import static es.princip.getp.domain.member.fixture.EmailFixture.email;

public class ClientFixture {

    public static Client client(final Long memberId) {
        return Client.builder()
            .memberId(memberId)
            .email(email())
            .address(address())
            .bankAccount(bankAccount())
            .build();
    }

    public static List<Client> clientList(final int size, final Long memberIdBias) {
        return LongStream.range(0, size)
            .mapToObj(i -> client(memberIdBias + i))
            .toList();
    }
}
