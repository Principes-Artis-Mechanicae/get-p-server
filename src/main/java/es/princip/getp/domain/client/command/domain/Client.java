package es.princip.getp.domain.client.command.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import es.princip.getp.domain.like.command.domain.Likeable;
import es.princip.getp.domain.member.command.domain.model.Email;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "client")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client extends BaseTimeEntity implements Likeable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @Embedded
    private Email email;

    //TODO: 주소기반산업지원서비스 API로 확장 예정
    @Embedded
    private Address address;

    @Embedded
    private BankAccount bankAccount;

    @Column(name = "member_id")
    private Long memberId;

    @Builder
    public Client(
        final Email email,
        final Address address,
        final BankAccount bankAccount,
        final Long memberId
    ) {
        this.email = email;
        this.address = address;
        this.bankAccount = bankAccount;
        this.memberId = memberId;
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