package es.princip.getp.domain.member.command.application;

import es.princip.getp.domain.member.command.application.command.CreateMemberCommand;
import es.princip.getp.domain.member.command.application.command.UpdateMemberCommand;
import es.princip.getp.domain.member.command.domain.model.Member;
import es.princip.getp.domain.member.command.domain.model.MemberRepository;
import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.domain.member.command.domain.model.ProfileImage;
import es.princip.getp.domain.member.command.domain.service.ServiceTermAgreementService;
import es.princip.getp.domain.member.command.infra.ProfileImageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static es.princip.getp.domain.member.fixture.EmailFixture.email;
import static es.princip.getp.domain.member.fixture.MemberFixture.member;
import static es.princip.getp.domain.member.fixture.NicknameFixture.nickname;
import static es.princip.getp.domain.member.fixture.PasswordFixture.password;
import static es.princip.getp.domain.member.fixture.PhoneNumberFixture.phoneNumber;
import static es.princip.getp.domain.member.fixture.ProfileImageFixture.profileImage;
import static es.princip.getp.infra.storage.fixture.ImageStorageFixture.imageMultiPartFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ServiceTermAgreementService agreementService;

    @Mock
    private ProfileImageServiceImpl profileImageService;

    @InjectMocks
    private MemberService memberService;

    @DisplayName("create()는")
    @Nested
    class Create {

        private final Long memberId = 1L;
        private final CreateMemberCommand command = new CreateMemberCommand(
            email(), password(), List.of(), MemberType.ROLE_PEOPLE
        );

        @DisplayName("회원을 생성한다.")
        @Test
        void create() {
            Member member = spy(Member.class);
            given(member.getMemberId()).willReturn(memberId);
            given(memberRepository.existsByEmail(command.email())).willReturn(false);
            willDoNothing().given(agreementService).agreeServiceTerms(any(Member.class), anyList());
            given(memberRepository.save(any(Member.class))).willReturn(member);

            Long memberId = memberService.create(command);

            assertThat(memberId).isEqualTo(member.getMemberId());
            verify(agreementService, times(1)).agreeServiceTerms(any(Member.class), anyList());
        }
    }

    @Nested
    @DisplayName("update()는")
    class Update {

        private final Long memberId = 1L;
        private final UpdateMemberCommand command = new UpdateMemberCommand(
            memberId, nickname(), phoneNumber()
        );

        @DisplayName("회원 정보를 수정한다.")
        @Test
        void update() {
            Member member = spy(Member.class);
            given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

            memberService.update(command);

            verify(member, times(1)).edit(command.nickname(), command.phoneNumber());
        }
    }

    @Nested
    @DisplayName("updateProfileImage()는")
    class UpdateProfileImage {

        private final Member member = spy(member(MemberType.ROLE_PEOPLE));
        private final MultipartFile image = imageMultiPartFile();

        @BeforeEach
        void setUp() {
            given(member.getMemberId()).willReturn(1L);
        }

        @DisplayName("프로필 이미지를 수정한다.")
        @Test
        void updateProfileImage() {
            ProfileImage profileImage = profileImage(member.getMemberId());
            given(member.hasProfileImage()).willReturn(true);
            given(member.getProfileImage()).willReturn(profileImage);
            given(memberRepository.findById(member.getMemberId())).willReturn(Optional.of(member));
            given(profileImageService.saveProfileImage(member, image)).willReturn(profileImage);

            memberService.changeProfileImage(member.getMemberId(), image);

            verify(profileImageService, times(1)).deleteProfileImage(profileImage);
            verify(profileImageService, times(1)).saveProfileImage(member, image);
            assertThat(member.getProfileImage()).isEqualTo(profileImage);
        }
    }
}