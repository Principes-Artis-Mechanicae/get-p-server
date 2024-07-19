package es.princip.getp.domain.project.command.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@DisplayName("지원자 모집 기간")
class ApplicationDurationTest {

    @Test
    void 시작일이_종료일보다_빠를_수_없다() {
       assertThatThrownBy(() ->
           ApplicationDuration.of(
               LocalDate.of(2024, 7, 17),
               LocalDate.of(2024, 7, 16)
           )
       ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 시작일이_종료일보다_이전이어야_한다() {
        assertThatCode(() ->
            ApplicationDuration.of(
                LocalDate.of(2024, 7, 16),
                LocalDate.of(2024, 7, 17)
            )
        ).doesNotThrowAnyException();
    }

    @Test
    void 시작일이_종료일과_같을_수_있다() {
        assertThatCode(() ->
            ApplicationDuration.of(
                LocalDate.of(2024, 7, 17),
                LocalDate.of(2024, 7, 17)
            )
        ).doesNotThrowAnyException();
    }

    @Test
    void 종료일이_지금보다_이전인_경우_모집은_마감된다() {
        LocalDate now = LocalDate.now();
        ApplicationDuration duration = ApplicationDuration.of(
            now.minusDays(2),
            now.minusDays(1)
        );
        assertThat(duration.isClosed()).isTrue();
    }

    @Test
    void 종료일이_오늘까지면_모집은_열려있다() {
        LocalDate now = LocalDate.now();
        ApplicationDuration duration = ApplicationDuration.of(
            now.minusDays(2),
            now
        );
        assertThat(duration.isClosed()).isFalse();
    }
}
