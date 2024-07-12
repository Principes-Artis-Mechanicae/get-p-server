package es.princip.getp.domain.member.domain.model;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "member",
    uniqueConstraints = {@UniqueConstraint(name = "uq_email", columnNames = {"email"})}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "member_id")
    private Long memberId;

    @Embedded
    @Getter
    private Email email;

    @Embedded
    @Getter
    private Password password;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_type")
    @Getter
    private MemberType memberType;

    @Embedded
    @Getter
    private Nickname nickname;

    @Embedded
    @Getter
    private PhoneNumber phoneNumber;

    @Embedded
    @Getter
    private ProfileImage profileImage;

    @ElementCollection
    @CollectionTable(
        name = "member_service_term_agreement",
        joinColumns = @JoinColumn(name = "member_id")
    )
    private Set<ServiceTermAgreement> agreements = new HashSet<>();

    private Member(
        final Email email,
        final Password password,
        final MemberType memberType
    ) {
        setEmail(email);
        setPassword(password);
        setMemberType(memberType);
    }

    public static Member of(
        final Email email,
        final Password password,
        final MemberType memberType
    ) {
        return new Member(email, password, memberType);
    }

    private void setEmail(final Email email) {
        if (email == null) {
            throw new IllegalArgumentException("이메일은 필수 입력 값입니다.");
        }
        this.email = email;
    }

    private void setMemberType(final MemberType memberType) {
        if (memberType == null) {
            throw new IllegalArgumentException("이메일은 필수 입력 값입니다.");
        }
        this.memberType = memberType;
    }

    private void setPassword(final Password password) {
        if (password == null) {
            throw new IllegalArgumentException("이메일은 필수 입력 값입니다.");
        }
        this.password = password;
    }

    private void setNickname(Nickname nickname) {
        if (nickname == null) {
            throw new IllegalArgumentException("닉네임은 필수 입력 값입니다.");
        }
        this.nickname = nickname;
    }

    private void setPhoneNumber(PhoneNumber phoneNumber) {
        if (phoneNumber == null) {
            throw new IllegalArgumentException("전화번호는 필수 입력 값입니다.");
        }
        this.phoneNumber = phoneNumber;
    }

    private void setProfileImage(ProfileImage profileImage) {
        if (profileImage == null) {
            throw new IllegalArgumentException("프로필 사진은 필수 입력 값입니다.");
        }
        this.profileImage = profileImage;
    }

    public void agreeServiceTerms(final Set<ServiceTermAgreement> agreements) {
        this.agreements.clear();
        this.agreements.addAll(agreements);
    }

    public void changeProfileImage(final ProfileImage profileImage) {
        setProfileImage(profileImage);
    }

    public boolean hasProfileImage() {
        return profileImage != null;
    }

    public void changeNickname(final Nickname nickname) {
        setNickname(nickname);
    }

    public void changePhoneNumber(final PhoneNumber phoneNumber) {
        setPhoneNumber(phoneNumber);
    }

    public void encodePassword(final PasswordEncoder encoder) {
        if (password == null) {
            throw new IllegalStateException("비밀번호가 설정되어 있지 않습니다.");
        }
        if (password.isEncoded()) {
            return;
        }
        this.password = password.encode(encoder);
    }

    public Set<ServiceTermAgreement> getServiceTermAgreements() {
        return Collections.unmodifiableSet(agreements);
    }
}
