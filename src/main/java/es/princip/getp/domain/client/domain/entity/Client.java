package es.princip.getp.domain.client.domain.entity;

import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.domain.client.dto.request.CreateClientRequest;
import es.princip.getp.domain.client.dto.request.UpdateClientRequest;
import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.people.domain.entity.PeopleLike;
import es.princip.getp.global.domain.values.Address;
import es.princip.getp.global.domain.values.BankAccount;
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

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_image_uri")
    private String profileImageUri;

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
        String nickname,
        String email,
        String phoneNumber,
        String profileImageUri,
        Address address,
        BankAccount bankAccount,
        final Member member
    ) {
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileImageUri = profileImageUri;
        this.address = address;
        this.bankAccount = bankAccount;
        this.member = member;
        member.setClient(this);
    }

    public static Client from(final Member member, final CreateClientRequest request) {
        return Client.builder()
            .nickname(request.nickname())
            .email(request.email() == null ? member.getEmail() : request.email())
            .phoneNumber(request.phoneNumber())
            .profileImageUri(request.profileImageUri())
            .address(request.address())
            .bankAccount(request.bankAccount())
            .member(member)
            .build();
    }

    public void update(final UpdateClientRequest request) {
        this.nickname = request.nickname();
        this.email = request.email();
        this.phoneNumber = request.phoneNumber();
        this.profileImageUri = request.profileImageUri();
        this.address = request.address();
        this.bankAccount = request.bankAccount();
    }
}