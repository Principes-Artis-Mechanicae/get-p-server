package es.princip.getp.domain.client.model;

import es.princip.getp.domain.common.model.BaseEntity;
import es.princip.getp.domain.like.command.domain.Likeable;
import es.princip.getp.domain.member.model.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Client extends BaseEntity implements Likeable {

    private Long clientId;
    @NotNull private Email email;
    private Address address; //TODO: 주소기반산업지원서비스 API로 확장 예정
    private BankAccount bankAccount;
    @NotNull private Long memberId;

    @Builder
    public Client(
        final Long clientId,
        final Email email,
        final Address address,
        final BankAccount bankAccount,
        final Long memberId,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);

        this.clientId = clientId;
        this.email = email;
        this.address = address;
        this.bankAccount = bankAccount;
        this.memberId = memberId;

        validate();
    }

    private void setEmail(final Email email) {
        if (email == null) {
            throw new IllegalArgumentException();
        }
        this.email = email;
    }

    private void setAddress(final Address address) {
        if (address == null) {
            throw new IllegalArgumentException();
        }
        this.address = address;
    }

    private void setBankAccount(final BankAccount bankAccount) {
        if (bankAccount == null) {
            throw new IllegalArgumentException();
        }
        this.bankAccount = bankAccount;
    }

    public void edit(final Email email, final Address address, final BankAccount bankAccount) {
        setEmail(email);
        setAddress(address);
        setBankAccount(bankAccount);
    }

    @Override
    public Long getId() {
        return this.clientId;
    }
}