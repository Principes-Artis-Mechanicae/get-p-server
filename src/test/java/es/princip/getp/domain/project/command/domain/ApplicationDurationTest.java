package es.princip.getp.domain.project.command.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("지원자 모집 기간")
class ApplicationDurationTest {

    private final LocalDate now = LocalDate.now();

    @Test
    void 종료일이_지금보다_이전인_경우_모집은_마감된다() {
        final ApplicationDuration duration = ApplicationDuration.of(
            now.minusDays(2),
            now.minusDays(1)
        );
        assertThat(duration.isClosed()).isTrue();
    }

    @Test
    void 종료일이_오늘까지면_모집은_열려있다() {
        final ApplicationDuration duration = ApplicationDuration.of(
            now.minusDays(2),
            now
        );
        assertThat(duration.isClosed()).isFalse();
    }
}
