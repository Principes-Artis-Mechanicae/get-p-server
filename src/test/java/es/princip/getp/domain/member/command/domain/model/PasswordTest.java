package es.princip.getp.domain.member.command.domain.model;

import es.princip.getp.domain.member.command.infra.SimplePasswordEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("비밀번호")
@ExtendWith(MockitoExtension.class)
class PasswordTest {

    @DisplayName("비밀번호는 8자 이상 20자 이하의 영문, 숫자, 특수문자를 포함해야 한다.")
    @Nested
    class Of {
        @Test
        void 비밀번호_입력_값_정상() {
            assertThat(Password.of("qwer1234!")).isNotNull();
        }

        @Test
        void 비밀번호는_8자_이상이어야_한다() {
            assertThatThrownBy(() -> Password.of("qwer"))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 비밀번호는_20자리_이하이어야_한다() {
            final String value = "abcdefghijklmnopqrstuvwxyz"; // 64자
            assertThatThrownBy(() -> Password.of(value))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 비밀번호는_숫자를_포함해야_한다() {
            final String value = "qwerqwer";
            assertThatThrownBy(() -> Password.of(value))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 비밀번호는_영문만_포함해야_한다() {
            final String value = "테스트";
            assertThatThrownBy(() -> Password.of(value))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 비밀번호는_특수문자를_포함해야_한다() {
            final String value = "qwer1234";
            assertThatThrownBy(() -> Password.of(value))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @DisplayName("비밀번호의 인코딩 값을 비교한다.")
    @Nested
    class IsMatch {
        private final PasswordEncoder encoder = new SimplePasswordEncoder();

        @Test
        void 인코딩한_비밀번호가_같으면_참() {
            Password password1 = Password.of("qwer1234!");
            Password password2 = Password.of("qwer1234!");

            assertThat(password1.isMatch(encoder, password2)).isTrue();
        }

        @Test
        void 인코딩한_비밀번호가_다르면_거짓() {
            Password password1 = Password.of("qwer1234!");
            Password password2 = Password.of("asdf1234!");

            assertThat(password1.isMatch(encoder, password2)).isFalse();
        }
    }
}