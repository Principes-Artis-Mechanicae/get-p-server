package es.princip.getp.domain.project.command.domain;

import es.princip.getp.common.domain.Duration;
import es.princip.getp.domain.common.infra.StubClockHolder;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectStatusTest {

    final LocalDate now = LocalDate.of(2024, 7, 14);
    final Clock clock = new StubClockHolder(now).getClock();

    @Test
    void 지원자_모집_기간이_시작했으면_프로젝트_상태는_지원_중이다() {
        final Duration applicationDuration = Duration.of(
            LocalDate.of(2024, 7, 1),
            LocalDate.of(2024, 7, 31)
        );

        final ProjectStatus status = ProjectStatus.determineStatus(applicationDuration, clock);

        assertThat(status).isEqualTo(ProjectStatus.APPLYING);
    }

    @Test
    void 지원자_모집_기간이_시작하지_않았으면_프로젝트_상태는_준비_중이다() {
        final Duration applicationDuration = Duration.of(
            LocalDate.of(2024, 7, 15),
            LocalDate.of(2024, 7, 31)
        );

        final ProjectStatus status = ProjectStatus.determineStatus(applicationDuration, clock);

        assertThat(status).isEqualTo(ProjectStatus.PREPARING);
    }
}