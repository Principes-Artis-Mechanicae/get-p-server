package es.princip.getp.domain.client.domain.entity;

import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.domain.client.dto.request.CreateClientRequest;
import es.princip.getp.domain.client.dto.request.UpdateClientRequest;
import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.people.domain.PeopleLike;
import es.princip.getp.global.domain.Address;
import es.princip.getp.global.domain.BankAccount;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "client")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "email")
    private String email;

    //TODO: 주소기반산업지원서비스 API로 확장 예정
    @Embedded
    private Address address;

    @Embedded
    private BankAccount bankAccount;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PeopleLike> peopleLikes = new ArrayList<>();

    @Builder
    public Client(
        final String email,
        final Address address,
        final BankAccount bankAccount,
        final Member member
    ) {
        this.email = email == null ? member.getEmail() : email;
        this.address = address;
        this.bankAccount = bankAccount;
        this.member = member;
    }

    public static Client from(
        final Member member,
        final CreateClientRequest request
    ) {
        return Client.builder()
            .email(request.email())
            .address(request.address())
            .bankAccount(request.bankAccount())
            .member(member)
            .build();
    }

    public String getNickname() {
        return this.member.getNickname();
    }

    public String getPhoneNumber() {
        return this.member.getPhoneNumber();
    }

    public String getProfileImageUri() {
        return this.member.getProfileImageUri();
    }

    public void update(final UpdateClientRequest request) {
        this.email = request.email();
        this.address = request.address();
        this.bankAccount = request.bankAccount();
    }
}