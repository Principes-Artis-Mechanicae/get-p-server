package es.princip.getp.domain.common.model;

import es.princip.getp.common.infra.StubClockHolder;
import es.princip.getp.domain.common.exception.StartDateIsAfterEndDateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Clock;
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

    @Nested
    class 주어진_날짜가_기간_내인지_확인할_수_있다 {

        final LocalDate date = LocalDate.of(2024, 7, 14);

        @Test
        void 주어진_날짜가_기간의_시작일_이후_종료일_이전인_경우_기간_내이다() {
            final Duration duration = Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            );

            assertThat(duration.isBetween(date)).isTrue();
        }

        @Test
        void 주어진_날짜가_기간의_시작일과_겹치는_경우_기간_내이다() {
            final Duration duration = Duration.of(
                LocalDate.of(2024, 7, 14),
                LocalDate.of(2024, 7, 31)
            );

            assertThat(duration.isBetween(date)).isTrue();
        }

        @Test
        void 주어진_날짜가_기간의_종료일과_겹치는_경우_기간_내이다() {
            final Duration duration = Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 14)
            );

            assertThat(duration.isBetween(date)).isTrue();
        }

        @Test
        void 주어진_날짜가_기간보다_이전인_경우_기간_외이다() {
            final Duration duration = Duration.of(
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 30)
            );

            assertThat(duration.isBetween(date)).isFalse();
        }

        @Test
        void 주어진_날짜가_기간보다_이후인_경우_기간_외이다() {
            final Duration duration = Duration.of(
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 8, 31)
            );

            assertThat(duration.isBetween(date)).isFalse();
        }
    }

    @Nested
    class 기간이_끝났는지_확인할_수_있다 {

        final LocalDate now = LocalDate.of(2024, 7, 14);
        final Clock clock = new StubClockHolder(now).getClock();

        @Test
        void 오늘이_기간_종료일_이전이면_기간은_끝난적이_없다() {
            final Duration duration = Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            );

            assertThat(duration.isEnded(clock)).isFalse();
        }

        @Test
        void 오늘이_기간_종료일이면_기간은_끝난적이_없다() {
            final Duration duration = Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 14)
            );

            assertThat(duration.isEnded(clock)).isFalse();
        }

        @Test
        void 오늘이_기간_종료일_이후면_기간은_끝난다() {
            final Duration duration = Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 7)
            );

            assertThat(duration.isEnded(clock)).isTrue();
        }
    }

    @Nested
    class 주어진_기간보다_이전인지_확인할_수_있다 {

        @Test
        void 주어진_기간의_시작일보다_종료일이_이전인_경우_이전이다() {
            final Duration duration = Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            );
            final Duration other = Duration.of(
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 8, 31)
            );

            assertThat(duration.isBefore(other)).isTrue();
        }

        @Test
        void 주어진_기간의_시작일과_종료일이_같은_경우_이전이_아니다() {
            final Duration duration = Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 8, 1)
            );
            final Duration other = Duration.of(
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 8, 31)
            );

            assertThat(duration.isBefore(other)).isFalse();
        }

        @Test
        void 주어진_기간과_겹치는_경우_이전이_아니다() {
            final Duration duration = Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            );
            final Duration other = Duration.of(
                LocalDate.of(2024, 7, 14),
                LocalDate.of(2024, 7, 21)
            );

            assertThat(duration.isBefore(other)).isFalse();
        }

        @Test
        void 주어진_기간의_종료일보다_시작일이_이후인_경우_이전이_아니다() {
            final Duration duration = Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            );
            final Duration other = Duration.of(
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 30)
            );

            assertThat(duration.isBefore(other)).isFalse();
        }
    }

    @Nested
    class 주어진_기간보다_이후인지_확인할_수_있다 {

        @Test
        void 주어진_기간의_시작일보다_종료일이_이전인_경우_이후가_아니다() {
            final Duration duration = Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            );
            final Duration other = Duration.of(
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 8, 31)
            );

            assertThat(duration.isAfter(other)).isFalse();
        }

        @Test
        void 주어진_기간의_종료일과_시작일이_같은_경우_이후가_아니다() {
            final Duration duration = Duration.of(
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 8, 31)
            );
            final Duration other = Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 8, 1)
            );

            assertThat(duration.isAfter(other)).isFalse();
        }

        @Test
        void 주어진_기간과_겹치는_경우_이후가_아니다() {
            final Duration duration = Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            );
            final Duration other = Duration.of(
                LocalDate.of(2024, 7, 14),
                LocalDate.of(2024, 7, 21)
            );

            assertThat(duration.isAfter(other)).isFalse();
        }

        @Test
        void 주어진_기간의_종료일보다_시작일이_이후인_경우_이후다() {
            final Duration duration = Duration.of(
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 8, 31)
            );
            final Duration other = Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            );

            assertThat(duration.isAfter(other)).isTrue();
        }
    }
}