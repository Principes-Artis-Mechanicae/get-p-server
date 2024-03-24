package es.princip.getp.global.domain.values;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankAccount {
    @Column(name = "bank")
    @NotBlank
    private String bank;

    @Column(name = "account_number")
    @NotBlank
    @Pattern(regexp = "^[^-]*$")
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
