package es.princip.getp.fixture.client;

import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.member.model.MemberId;

import static es.princip.getp.fixture.client.AddressFixture.address;
import static es.princip.getp.fixture.client.BankAccountFixture.bankAccount;
import static es.princip.getp.fixture.common.EmailFixture.email;

public class ClientFixture {

    public static Client client(final MemberId memberId) {
        return Client.builder()
            .memberId(memberId)
            .email(email())
            .address(address())
            .bankAccount(bankAccount())
            .build();
    }
}
