package es.princip.getp.persistence.adapter.client;

import es.princip.getp.persistence.adapter.BaseTimeJpaEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "client")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class ClientJpaEntity extends BaseTimeJpaEntity {

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    @Column(name = "email")
    private String email;

    @Embedded
    private AddressJpaVO address;

    @Embedded
    private BankAccountJpaVO bankAccount;

    @Column(name = "member_id")
    private Long memberId;
}