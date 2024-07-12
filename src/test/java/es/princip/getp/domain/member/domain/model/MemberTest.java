package es.princip.getp.domain.member.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static es.princip.getp.domain.member.fixture.EmailFixture.email;
import static es.princip.getp.domain.member.fixture.PasswordFixture.password;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class MemberTest {

    @Test
    void agreeServiceTerms() {
        Set<ServiceTermAgreement> agreements = spy(new HashSet<>());
        Member member = Member.of(email(), password(), MemberType.ROLE_PEOPLE);

        member.agreeServiceTerms(agreements);

        assertThat(member.getServiceTermAgreements()).isEqualTo(agreements);
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
    void changeNickname() {
        Member member = Member.of(email(), password(), MemberType.ROLE_PEOPLE);

        Nickname newNickname = Nickname.of("new nickname");
        member.changeNickname(newNickname);

        assertThat(member.getNickname()).isEqualTo(newNickname);
    }

    @Test
    void changePhoneNumber() {
        Member member = Member.of(email(), password(), MemberType.ROLE_PEOPLE);

        PhoneNumber newPhoneNumber = PhoneNumber.of("01087654321");
        member.changePhoneNumber(newPhoneNumber);

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