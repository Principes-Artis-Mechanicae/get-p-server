package es.princip.getp.domain.client.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankAccount {
    @Column(name = "bank")
    @NotBlank
    private String bank;

    @Column(name = "account_number")
    @NotBlank
    @Pattern(regexp = "^[0-9]+$", message = "{validation.constraints.AccountNumber.message}") // 계좌 번호는 숫자만 포함
    private String accountNumber;

    @Column(name = "account_holder")
    @NotBlank
    private String accountHolder;

    private BankAccount(String bank, String accountNumber, String accountHolder) {
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
    }

    public static BankAccount of(String bank, String accountNumber, String accountHolder) {
        return new BankAccount(bank, accountNumber, accountHolder);
    }
}
