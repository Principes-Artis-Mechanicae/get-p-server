package es.princip.getp.domain.member.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import es.princip.getp.domain.auth.dto.request.SignUpRequest;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.entity.MemberType;
import es.princip.getp.domain.member.exception.MemberErrorCode;
import es.princip.getp.domain.member.repository.MemberRepository;
import es.princip.getp.fixture.SignUpFixture;
import es.princip.getp.global.exception.BusinessLogicException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

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
            assertEquals(exception.getCode(), MemberErrorCode.MEMBER_NOT_FOUND.name());
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
            assertEquals(exception.getCode(), MemberErrorCode.MEMBER_NOT_FOUND.name());
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
            SignUpRequest signUpRequest = SignUpFixture.createSignUpRequest(email, password, memberType);
            Member member = Member.builder()
                .email(email)
                .password(password)
                .memberType(memberType)
                .build();
            when(memberRepository.save(any())).thenReturn(member);
            when(passwordEncoder.encode(password)).thenReturn(password);

            assertEquals(memberService.create(signUpRequest, passwordEncoder), member);
        }
    }
}