package es.princip.getp.persistence.adapter.member;

import es.princip.getp.common.domain.BaseTimeEntity;
import es.princip.getp.domain.member.model.MemberType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "member",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_email", columnNames = {"email"}),
        @UniqueConstraint(name = "uq_nickname", columnNames = {"nickname"})
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJpaEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_type")
    private MemberType memberType;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "member_service_term_agreement",
        joinColumns = @JoinColumn(name = "member_id")
    )
    private Set<ServiceTermAgreementJpaVO> serviceTermAgreements = new HashSet<>();
}
