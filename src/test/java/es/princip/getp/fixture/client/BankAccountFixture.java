package es.princip.getp.fixture.client;

import es.princip.getp.domain.client.command.domain.BankAccount;

public class BankAccountFixture {

    public static final String BANK = "대구은행";
    public static final String ACCOUNT_NUMBER = "123456789";
    public static final String ACCOUNT_HOLDER = "신찬규";

    public static BankAccount bankAccount() {
        return BankAccount.of(BANK, ACCOUNT_NUMBER, ACCOUNT_HOLDER);
    }
}
