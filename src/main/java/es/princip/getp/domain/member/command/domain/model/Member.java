package es.princip.getp.domain.member.command.domain.model;

import es.princip.getp.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "member",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_email", columnNames = {"email"}),
        @UniqueConstraint(name = "uq_nickname", columnNames = {"nickname"})
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Validated
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Embedded
    @NotNull
    private Email email;

    @Embedded
    @NotNull
    private Password password;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_type")
    @NotNull
    private MemberType memberType;

    @Embedded
    private Nickname nickname;

    @Embedded
    private PhoneNumber phoneNumber;

    @Embedded
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
        Objects.requireNonNull(email);
        Objects.requireNonNull(password);

        this.email = email;
        setPassword(password);
        this.memberType = memberType;
    }

    public static Member of(
        final Email email,
        final Password password,
        final MemberType memberType
    ) {
        return new Member(email, password, memberType);
    }

    private void setPassword(final Password password) {
        Objects.requireNonNull(password);
        this.password = password;
    }

    private void setNickname(final Nickname nickname) {
        Objects.requireNonNull(nickname);
        this.nickname = nickname;
    }

    private void setPhoneNumber(final PhoneNumber phoneNumber) {
        Objects.requireNonNull(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    private void setProfileImage(final ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    /**
     * 서비스 약관에 동의한다.
     *
     * @param agreements 서비스 약관 동의
     */
    public void agreeServiceTerms(final Set<ServiceTermAgreement> agreements) {
        this.agreements.clear();
        this.agreements.addAll(agreements);
    }

    /**
     * 프로필 이미지를 변경한다.
     *
     * @param profileImage 변경할 프로필 이미지
     */
    public void changeProfileImage(final ProfileImage profileImage) {
        setProfileImage(profileImage);
    }

    /**
     * 프로필 이미지가 존재하는지 확인한다.
     *
     * @return 프로필 이미지가 존재하는 경우 true, 그렇지 않은 경우 false
     */
    public boolean hasProfileImage() {
        return profileImage != null;
    }

    /**
     * 회원 정보를 수정한다.
     *
     * @param nickname 닉네임
     * @param phoneNumber 전화번호
     */
    public void edit(final Nickname nickname, final PhoneNumber phoneNumber) {
        setNickname(nickname);
        setPhoneNumber(phoneNumber);
    }

    /**
     * 비밀번호를 암호화한다.
     *
     * @param encoder 비밀번호 인코더
     */
    public void encodePassword(final PasswordEncoder encoder) {
        if (password == null) {
            throw new IllegalStateException("비밀번호가 설정되어 있지 않습니다.");
        }
        if (password.isEncoded()) {
            return;
        }
        this.password = password.encode(encoder);
    }

    /**
     * 서비스 약관 동의 목록을 반환한다.
     *
     * @return 서비스 약관 동의 목록
     */
    public Set<ServiceTermAgreement> getServiceTermAgreements() {
        return Collections.unmodifiableSet(agreements);
    }
}
