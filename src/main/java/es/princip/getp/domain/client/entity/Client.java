package es.princip.getp.domain.client.entity;

import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.domain.client.dto.request.UpdateClientRequest;
import es.princip.getp.domain.member.entity.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "client")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_image_uri")
    private String profileImageUri;

    // 추후 주소기반산업지원서비스 API로 확장 예정
    @Column(name = "address")
    private String address;

    @Column(name = "account_number")
    private String accountNumber;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Member member;

    @Builder
    public Client(String name, String email, String phoneNumber, String profileImageUri, String address,
            String accountNumber, final Member member) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileImageUri = profileImageUri;
        this.address = address;
        this.accountNumber = accountNumber;
        this.member = member;
    }

    public void update(final UpdateClientRequest request) {
        this.name = request.name();
        this.email = request.email();
        this.phoneNumber = request.phoneNumber();
        this.profileImageUri = request.profileImageUri();
        this.address = request.address();
        this.accountNumber = request.accountNumber();
    }
}