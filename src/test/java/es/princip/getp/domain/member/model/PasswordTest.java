package es.princip.getp.domain.member.model;

import es.princip.getp.domain.common.exception.NotValidDomainModelException;
import es.princip.getp.domain.member.infra.SimplePasswordEncoder;
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

    @Nested
    class 비밀번호는_8자_이상_20자_이하의_영문_숫자_특수문자를_포함해야_한다 {

        @Test
        void 비밀번호_입력_값_정상() {
            assertThat(Password.from("qwer1234!")).isNotNull();
        }

        @Test
        void 비밀번호는_8자_이상이어야_한다() {
            assertThatThrownBy(() -> Password.from("qwer"))
                .isInstanceOf(NotValidDomainModelException.class);
        }

        @Test
        void 비밀번호는_20자리_이하이어야_한다() {
            final String value = "abcdefghijklmnopqrstuvwxyz"; // 64자
            assertThatThrownBy(() -> Password.from(value))
                .isInstanceOf(NotValidDomainModelException.class);
        }

        @Test
        void 비밀번호는_숫자를_포함해야_한다() {
            final String value = "qwerqwer";
            assertThatThrownBy(() -> Password.from(value))
                .isInstanceOf(NotValidDomainModelException.class);
        }

        @Test
        void 비밀번호는_영문만_포함해야_한다() {
            final String value = "테스트";
            assertThatThrownBy(() -> Password.from(value))
                .isInstanceOf(NotValidDomainModelException.class);
        }

        @Test
        void 비밀번호는_특수문자를_포함해야_한다() {
            final String value = "qwer1234";
            assertThatThrownBy(() -> Password.from(value))
                .isInstanceOf(NotValidDomainModelException.class);
        }
    }

    @Nested
    class 비밀번호의_인코딩_값을_비교한다 {

        private final PasswordEncoder encoder = new SimplePasswordEncoder();

        @Test
        void 인코딩한_비밀번호가_같으면_참() {
            Password password1 = Password.from("qwer1234!");
            Password password2 = Password.from("qwer1234!");

            assertThat(password1.isMatch(encoder, password2)).isTrue();
        }

        @Test
        void 인코딩한_비밀번호가_다르면_거짓() {
            Password password1 = Password.from("qwer1234!");
            Password password2 = Password.from("asdf1234!");

            assertThat(password1.isMatch(encoder, password2)).isFalse();
        }
    }
}