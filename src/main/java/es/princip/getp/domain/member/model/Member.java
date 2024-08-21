package es.princip.getp.domain.member.model;

import es.princip.getp.common.domain.BaseTimeEntity;
import es.princip.getp.domain.member.exception.NotAgreedAllRequiredServiceTermException;
import es.princip.getp.domain.serviceTerm.model.ServiceTermTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    private Long memberId;
    private Email email;
    private Password password;
    private MemberType memberType;
    private Nickname nickname;
    private PhoneNumber phoneNumber;
    private ProfileImage profileImage;
    private Set<ServiceTermAgreement> serviceTermAgreements = new HashSet<>();

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
     */
    public void agreeServiceTerms(
        final Set<ServiceTermTag> requiredTags,
        final Set<ServiceTermAgreementData> agreements
    ) {
        final Set<ServiceTermTag> agreedTags = agreements.stream()
            .filter(ServiceTermAgreementData::agreed)
            .map(ServiceTermAgreementData::tag)
            .collect(Collectors.toSet());
        if (!agreedTags.containsAll(requiredTags)) {
            throw new NotAgreedAllRequiredServiceTermException();
        }
        this.serviceTermAgreements = agreements.stream()
            .map(agreement -> ServiceTermAgreement.of(agreement.tag(), agreement.agreed()))
            .collect(Collectors.toSet());
    }

    /**
     * 프로필 이미지를 등록한다.
     */
    public void registerProfileImage(final ProfileImage profileImage) {
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
        return Collections.unmodifiableSet(serviceTermAgreements);
    }
}
