package es.princip.getp.domain.client.command.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import es.princip.getp.domain.member.command.domain.model.Email;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "client")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client extends BaseTimeEntity {

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

    @ElementCollection
    @CollectionTable(
        name = "client_people_like",
        joinColumns = @JoinColumn(name = "client_id")
    )
    private Set<Long> peopleLikes = new HashSet<>();

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

    private boolean alreadyLikedPeople(final Long peopleId) {
        return peopleLikes.contains(peopleId);
    }

    public void likePeople(final Long peopleId) {
        if (alreadyLikedPeople(peopleId)) {
            throw new IllegalArgumentException("이미 좋아요를 누른 사람입니다.");
        }
        peopleLikes.add(peopleId);
    }

    public void unlikePeople(final Long peopleId) {
        if (!alreadyLikedPeople(peopleId)) {
            throw new IllegalArgumentException("좋아요를 누르지 않은 사람입니다.");
        }
        peopleLikes.remove(peopleId);
    }
}