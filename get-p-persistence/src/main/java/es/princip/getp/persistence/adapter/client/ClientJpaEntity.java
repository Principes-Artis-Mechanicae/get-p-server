package es.princip.getp.persistence.adapter.client;

import es.princip.getp.persistence.adapter.BaseTimeJpaEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "client")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class ClientJpaEntity extends BaseTimeJpaEntity {

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Embedded
    private AddressJpaVO address;

    @Embedded
    private BankAccountJpaVO bankAccount;

    @Column(name = "member_id")
    private Long memberId;

    @Builder
    public ClientJpaEntity(
        final Long id,
        final String email,
        final AddressJpaVO address,
        final BankAccountJpaVO bankAccount,
        final Long memberId,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);

        this.id = id;
        this.email = email;
        this.address = address;
        this.bankAccount = bankAccount;
        this.memberId = memberId;
    }
}