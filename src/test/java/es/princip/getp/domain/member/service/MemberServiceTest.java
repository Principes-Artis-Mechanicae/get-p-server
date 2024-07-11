package es.princip.getp.domain.member.service;

import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.member.domain.MemberType;
import es.princip.getp.domain.member.dto.request.CreateMemberRequest;
import es.princip.getp.domain.member.dto.request.UpdateMemberRequest;
import es.princip.getp.domain.member.exception.MemberErrorCode;
import es.princip.getp.domain.member.repository.MemberRepository;
import es.princip.getp.domain.serviceTerm.fixture.ServiceTermFixture;
import es.princip.getp.domain.serviceTerm.service.ServiceTermService;
import es.princip.getp.domain.storage.service.ImageStorageService;
import es.princip.getp.global.exception.BusinessLogicException;
import es.princip.getp.global.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static es.princip.getp.domain.member.fixture.MemberFixture.createMember;
import static es.princip.getp.domain.storage.fixture.ImageStorageFixture.createImageMultiPartFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ServiceTermService serviceTermService;

    @Mock
    private ImageStorageService imageStorageService;

    @InjectMocks
    private MemberService memberService;

    @Nested
    @DisplayName("update()는")
    class Update {

        private Member mockMember;
        private final UpdateMemberRequest request = new UpdateMemberRequest(
            "newNickname",
            "010-9999-8888"
        );

        @BeforeEach
        void setUp() {
            mockMember = Mockito.spy(createMember());
            given(mockMember.getMemberId()).willReturn(1L);
        }

        @DisplayName("회원 정보를 수정한다.")
        @Test
        void update() {
            given(memberRepository.findById(mockMember.getMemberId())).willReturn(Optional.of(mockMember));

            memberService.update(mockMember.getMemberId(), request);

            assertThat(mockMember.getNickname()).isEqualTo(request.nickname());
            assertThat(mockMember.getPhoneNumber()).isEqualTo(request.phoneNumber());
        }
    }

    @Nested
    @DisplayName("updateProfileImage()는")
    class UpdateProfileImage {
        @Test
        void updateProfileImage() throws IOException {
            Member member = createMember();
            MultipartFile image = createImageMultiPartFile();
            Path path = Paths.get("images").resolve(String.valueOf(member.getMemberId())).resolve("profile");
            Path imagePath = path.resolve("image.jpeg");
            given(memberRepository.findById(member.getMemberId())).willReturn(Optional.of(member));
            given(imageStorageService.storeImage(any(), eq(image))).willReturn(imagePath);

            URI profileImageUri = memberService.updateProfileImage(member.getMemberId(), image);

            assertThat(profileImageUri).isEqualTo(URI.create("/" + PathUtil.toURI(imagePath)));
            assertThat(member.getProfileImageUri()).isEqualTo(profileImageUri.toString());
        }
    }

    @Nested
    @DisplayName("existsByEmail()은")
    class ExistsByEmail {

        @DisplayName("이메일이 존재할 경우 true를 반환한다.")
        @Test
        void existsByEmail() {
            String email = "test@exmaple.com";
            when(memberRepository.existsByEmail(email)).thenReturn(true);

            assertTrue(memberService.existsByEmail(email));
        }

        @DisplayName("이메일이 존재하지 않을 경우 false를 반환한다.")
        @Test
        void existsByEmail_WhenEmailExists_ShouldReturnFalse() {
            String email = "test@exmaple.com";
            when(memberRepository.existsByEmail(email)).thenReturn(false);

            assertFalse(memberService.existsByEmail(email));
        }
    }

    @Nested
    @DisplayName("getByMemberId()는")
    class GetByMemberId {

        @DisplayName("memberId로 회원을 검색한다.")
        @Test
        void getByMemberId() {
            Long memberId = 1L;
            String email = "test@example.com";
            String password = "password";
            MemberType memberType = MemberType.ROLE_PEOPLE;
            Member member = Member.builder()
                .email(email)
                .password(password)
                .memberType(memberType)
                .build();
            when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

            assertEquals(memberService.getByMemberId(memberId), member);
        }

        @DisplayName("존재하지 않는 회원일 경우 실패한다.")
        @Test
        void getByMemberId_WhenMemberNotExists_ShouldThrowException() {
            Long memberId = 1L;
            when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class,
                    () -> memberService.getByMemberId(memberId));
            assertEquals(exception.getErrorCode(), MemberErrorCode.MEMBER_NOT_FOUND);
        }
    }

    @DisplayName("getByEmail()은")
    @Nested
    class GetByEmail {

        @DisplayName("이메일로 회원을 검색한다.")
        @Test
        void getByEmail() {
            String email = "test@example.com";
            String password = "password";
            MemberType memberType = MemberType.ROLE_PEOPLE;
            Member member = Member.builder()
                .email(email)
                .password(password)
                .memberType(memberType)
                .build();
            when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));

            assertEquals(memberService.getByEmail(email), member);
        }

        @DisplayName("존재하지 않는 회원일 경우 실패한다.")
        @Test
        void getByEmail_WhenMemberNotExists_ShouldThrowException() {
            String email = "test@example.com";
            when(memberRepository.findByEmail(email)).thenReturn(Optional.empty());

            BusinessLogicException exception =
                assertThrows(BusinessLogicException.class, () -> memberService.getByEmail(email));
            assertEquals(exception.getErrorCode(), MemberErrorCode.MEMBER_NOT_FOUND);
        }
    }

    @DisplayName("create()는")
    @Nested
    class Create {

        @DisplayName("회원을 생성한다.")
        @Test
        void create() {
            String email = "test@example.com";
            String password = "password";
            MemberType memberType = MemberType.ROLE_PEOPLE;
            CreateMemberRequest request = new CreateMemberRequest(
                email,
                password,
                List.of(ServiceTermFixture.createServiceTermAgreementRequest()),
                memberType);
            Member member = Member.builder()
                .email(email)
                .password(password)
                .memberType(memberType)
                .build();
            given(memberRepository.save(any())).willReturn(member);
            given(serviceTermService.getByTag(any())).willReturn(any());
            given(serviceTermService.isAgreedAllRequiredServiceTerms(
                request.serviceTerms())).willReturn(true);

            assertThat(memberService.create(request)).isEqualTo(member);
        }
    }
}