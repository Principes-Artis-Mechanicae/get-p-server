package es.princip.getp.domain.member.model;

import es.princip.getp.domain.serviceTerm.model.ServiceTermTag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static es.princip.getp.domain.member.fixture.EmailFixture.email;
import static es.princip.getp.domain.member.fixture.PasswordFixture.password;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
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
    void changeProfileImage() {
        Member member = Member.of(email(), password(), MemberType.ROLE_PEOPLE);
        ProfileImage profileImage = mock(ProfileImage.class);

        member.changeProfileImage(profileImage);

        assertThat(member.getProfileImage()).isEqualTo(profileImage);
    }

    @Test
    void hasProfileImage_WhenMemberHasProfileImage_ShouldReturn_True() {
        Member member = Member.of(email(), password(), MemberType.ROLE_PEOPLE);
        ProfileImage profileImage = mock(ProfileImage.class);

        member.changeProfileImage(profileImage);

        assertThat(member.hasProfileImage()).isTrue();
    }

    @Test
    void hasProfileImage_WhenMemberDoesNotHaveProfileImage_ShouldReturn_False() {
        Member member = Member.of(email(), password(), MemberType.ROLE_PEOPLE);

        assertThat(member.hasProfileImage()).isFalse();
    }

    @Test
    void edit() {
        Member member = Member.of(email(), password(), MemberType.ROLE_PEOPLE);

        Nickname newNickname = Nickname.of("new nickname");
        PhoneNumber newPhoneNumber = PhoneNumber.of("01087654321");
        member.edit(newNickname, newPhoneNumber);

        assertThat(member.getNickname()).isEqualTo(newNickname);
        assertThat(member.getPhoneNumber()).isEqualTo(newPhoneNumber);
    }

    @Test
    void encodePassword() {
        Member member = Member.of(email(), password(), MemberType.ROLE_PEOPLE);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        given(encoder.encode(anyString())).willReturn(anyString());

        member.encodePassword(encoder);

        assertThat(member.getPassword()).isNotEqualTo(password());
    }
}