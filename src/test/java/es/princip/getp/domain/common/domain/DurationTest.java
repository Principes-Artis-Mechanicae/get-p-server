package es.princip.getp.domain.common.domain;

import es.princip.getp.domain.common.exception.StartDateIsAfterEndDateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("기간")
class DurationTest {

    @Nested
    class 시작일과_종료일을_가진다 {

        @Test
        void 시작일이_종료일보다_빠를_수_없다() {
            assertThatThrownBy(() ->
                Duration.of(
                    LocalDate.of(2024, 7, 17),
                    LocalDate.of(2024, 7, 16)
                )
            ).isInstanceOf(StartDateIsAfterEndDateException.class);
        }

        @Test
        void 시작일이_종료일보다_이전이어야_한다() {
            assertThatCode(() ->
                Duration.of(
                    LocalDate.of(2024, 7, 16),
                    LocalDate.of(2024, 7, 17)
                )
            ).doesNotThrowAnyException();
        }

        @Test
        void 시작일이_종료일과_같을_수_있다() {
            assertThatCode(() ->
                Duration.of(
                    LocalDate.of(2024, 7, 17),
                    LocalDate.of(2024, 7, 17)
                )
            ).doesNotThrowAnyException();
        }
    }

    @Nested
    class 시작일과_종료일의_차이를_구할_수_있다 {

        @Test
        void 주어진_날짜의_달이_같은_경우 () {
            final int dayOfMonth = 16;
            final int bias = 7;
            final Duration duration = Duration.of(
                LocalDate.of(2024, 7, dayOfMonth),
                LocalDate.of(2024, 7, dayOfMonth + bias)
            );

            assertThat(duration.days()).isEqualTo(bias);
        }

        @Test
        void 주어진_날짜의_달이_다른_경우() {
            final Duration duration = Duration.of(
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 7, 1)
            );

            assertThat(duration.days()).isEqualTo(30);
        }
    }
}