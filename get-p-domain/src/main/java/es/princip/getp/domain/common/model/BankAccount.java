package es.princip.getp.domain.common.model;

import es.princip.getp.domain.support.BaseModel;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class BankAccount extends BaseModel {

    @NotBlank private final String bank;
    @NotBlank private final String accountNumber;
    @NotBlank private final String accountHolder;

    public BankAccount(
        final String bank,
        final String accountNumber,
        final String accountHolder
    ) {
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;

        validate();
    }
}
