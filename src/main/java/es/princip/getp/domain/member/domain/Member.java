package es.princip.getp.domain.member.domain;

import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.domain.member.dto.request.CreateMemberRequest;
import es.princip.getp.domain.member.dto.request.UpdateMemberRequest;
import es.princip.getp.domain.serviceTerm.domain.ServiceTermAgreement;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member",
        uniqueConstraints = {@UniqueConstraint(name = "UniqueEmail", columnNames = {"email"})})
public class Member extends BaseTimeEntity {
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

    @Column(name = "profile_image_uri")
    private String profileImageUri;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ServiceTermAgreement> serviceTermAgreements = new ArrayList<>();

    @Builder
    public Member(
        final String email,
        final String password,
        final MemberType memberType,
        final String nickname,
        final String profileImageUri,
        final String phoneNumber,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        this.email = email;
        this.password = password;
        this.memberType = memberType;
        this.nickname = nickname;
        this.profileImageUri = profileImageUri;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update(final UpdateMemberRequest request) {
        this.nickname = request.nickname();
        this.phoneNumber = request.phoneNumber();
    }

    public static Member from(final CreateMemberRequest request) {
        return Member.builder()
            .email(request.email())
            .password(request.password())
            .memberType(request.memberType())
            .build();
    }

    public void updateProfileImageUri(URI profileImageUri) {
        this.profileImageUri = profileImageUri.toString();
    }

    public boolean hasProfileImage() {
        return profileImageUri != null;
    }
}
