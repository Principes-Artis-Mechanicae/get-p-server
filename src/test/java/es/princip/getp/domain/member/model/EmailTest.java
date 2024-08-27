package es.princip.getp.domain.member.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("이메일")
class EmailTest {

    @Nested
    class Of {
        @Test
        void of_WhenEmailIsValid_ShouldReturnEmail() {
            assertThat(Email.of("test@example.com")).isNotNull();
        }

        @Test
        void of_WhenEmailIsNotValid_ShouldThrowException() {
            assertThatThrownBy(() -> Email.of("test"))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class IsMatch {
        @Test
        void isMatch_WhenEmailIsSame_ShouldReturnTrue() {
            Email email1 = Email.of("test1@example.com");
            Email email2 = Email.of("test1@example.com");

            assertThat(email1.isMatch(email2)).isTrue();
        }

        @Test
        void isMatch_WhenEmailIsDifferent_ShouldReturnFalse() {
            Email email1 = Email.of("test1@example.com");
            Email email2 = Email.of("test2@example.com");

            assertThat(email1.isMatch(email2)).isFalse();
        }
    }
}