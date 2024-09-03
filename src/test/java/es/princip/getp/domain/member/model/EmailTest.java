package es.princip.getp.domain.member.model;

import es.princip.getp.domain.common.model.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("이메일")
class EmailTest {

    @Nested
    class 이메일_형식을_지켜야_한다 {

        @Test
        void 이메일_형식을_지키는_경우() {
            assertThat(Email.of("test@example.com")).isNotNull();
        }
    }

    @Nested
    class 다른_이메일과_일치한지_비교할_수_있다 {

        @Test
        void 다른_이메일과_같으면_참이다() {
            final Email email1 = Email.of("test1@example.com");
            final Email email2 = Email.of("test1@example.com");

            assertThat(email1.isMatch(email2)).isTrue();
        }

        @Test
        void 다른_이메일과_같으면_거짓이다() {
            final Email email1 = Email.of("test1@example.com");
            final Email email2 = Email.of("test2@example.com");

            assertThat(email1.isMatch(email2)).isFalse();
        }
    }
}