package es.princip.getp.domain.member.model;

import es.princip.getp.domain.common.model.PhoneNumber;
import es.princip.getp.domain.member.exception.NotAgreedAllRequiredServiceTermException;
import es.princip.getp.domain.member.infra.SimplePasswordEncoder;
import es.princip.getp.domain.serviceTerm.model.ServiceTermTag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static es.princip.getp.fixture.common.EmailFixture.email;
import static es.princip.getp.fixture.member.PasswordFixture.password;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

@DisplayName("회원")
@ExtendWith(MockitoExtension.class)
class MemberTest {

    @Nested
    class 회원은_서비스_약관에_동의할_수_있다 {

        private final Set<ServiceTermTag> requiredTags = Set.of(
            new ServiceTermTag("필수 서비스 약관1"),
            new ServiceTermTag("필수 서비스 약관2")
        );

        @Test
        void 모든_필수_서비스_약관에_동의한_경우() {
            final Set<ServiceTermAgreementData> agreements = Set.of(
                new ServiceTermAgreementData(new ServiceTermTag("필수 서비스 약관1"), true),
                new ServiceTermAgreementData(new ServiceTermTag("필수 서비스 약관2"), true),
                new ServiceTermAgreementData(new ServiceTermTag("서비스 약관1"), true),
                new ServiceTermAgreementData(new ServiceTermTag("서비스 약관2"), false)
            );
            final Member member = Member.of(email(), password(), MemberType.ROLE_PEOPLE);

            member.agreeServiceTerms(requiredTags, agreements);

            assertThat(member.getServiceTermAgreements()).containsExactlyInAnyOrder(
                ServiceTermAgreement.of(new ServiceTermTag("필수 서비스 약관1"), true),
                ServiceTermAgreement.of(new ServiceTermTag("필수 서비스 약관2"), true),
                ServiceTermAgreement.of(new ServiceTermTag("서비스 약관1"), true),
                ServiceTermAgreement.of(new ServiceTermTag("서비스 약관2"), false)
            );
        }

        @Test
        void 모든_필수_서비스_약관에_동의하지_않은_경우() {
            final Set<ServiceTermAgreementData> agreements = Set.of(
                new ServiceTermAgreementData(new ServiceTermTag("필수 서비스 약관1"), true),
                new ServiceTermAgreementData(new ServiceTermTag("필수 서비스 약관2"), false),
                new ServiceTermAgreementData(new ServiceTermTag("서비스 약관1"), true),
                new ServiceTermAgreementData(new ServiceTermTag("서비스 약관2"), false)
            );
            final Member member = Member.of(email(), password(), MemberType.ROLE_PEOPLE);

            assertThatThrownBy(() -> member.agreeServiceTerms(requiredTags, agreements))
                .isInstanceOf(NotAgreedAllRequiredServiceTermException.class);
        }
    }

    @Test
    void 회원은_프로필_이미지를_등록할_수_있다() {
        final Member member = Member.of(email(), password(), MemberType.ROLE_PEOPLE);
        final ProfileImage profileImage = mock(ProfileImage.class);

        member.registerProfileImage(profileImage);

        assertThat(member.getProfileImage()).isEqualTo(profileImage);
    }

    @Nested
    class 회원은_프로필_이미지를_등록했는지_확인할_수_있다 {

        @Test
        void 프로필_이미지를_등록한_경우() {
            final Member member = Member.of(email(), password(), MemberType.ROLE_PEOPLE);
            final ProfileImage profileImage = mock(ProfileImage.class);
            member.registerProfileImage(profileImage);

            assertThat(member.hasProfileImage()).isTrue();
        }

        @Test
        void 프로필_이미지를_등록하지_않은_경우() {
            final Member member = Member.of(email(), password(), MemberType.ROLE_PEOPLE);

            assertThat(member.hasProfileImage()).isFalse();
        }
    }

    @Test
    void 회원은_자신의_회원_정보를_수정할_수_있다() {
        final Member member = Member.of(email(), password(), MemberType.ROLE_PEOPLE);

        final Nickname newNickname = Nickname.from("new nickname");
        final PhoneNumber newPhoneNumber = PhoneNumber.from("01087654321");
        member.edit(newNickname, newPhoneNumber);

        assertThat(member.getNickname()).isEqualTo(newNickname);
        assertThat(member.getPhoneNumber()).isEqualTo(newPhoneNumber);
    }

    @Test
    void 회원은_비밀번호를_암호화_할_수_있다() {
        final Member member = Member.of(email(), password(), MemberType.ROLE_PEOPLE);
        final PasswordEncoder encoder = new SimplePasswordEncoder();

        member.encodePassword(encoder);

        assertThat(encoder.matches(password().getValue(), member.getPassword().getValue()))
            .isTrue();
    }
}