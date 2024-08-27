package es.princip.getp.persistence.adapter.member;

import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.persistence.adapter.BaseTimeJpaEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "member",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_email", columnNames = {"email"}),
        @UniqueConstraint(name = "uq_nickname", columnNames = {"nickname"})
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJpaEntity extends BaseTimeJpaEntity {

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
    private String profileImage;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "member_service_term_agreement",
        joinColumns = @JoinColumn(name = "member_id")
    )
    private Set<ServiceTermAgreementJpaVO> serviceTermAgreements = new HashSet<>();

    @Builder
    public MemberJpaEntity(
        final Long memberId,
        final String email,
        final String password,
        final MemberType memberType,
        final String nickname,
        final String phoneNumber,
        final String profileImage,
        final Set<ServiceTermAgreementJpaVO> serviceTermAgreements,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);

        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.memberType = memberType;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.serviceTermAgreements = serviceTermAgreements;
    }
}
