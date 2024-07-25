package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.common.domain.Duration;
import es.princip.getp.domain.common.infra.StubClockHolder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.Clock;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("프로젝트")
class ProjectTest {

    final LocalDate now = LocalDate.of(2024, 7, 1);
    final Clock clock = new StubClockHolder(now).getClock();

    @Test
    void 지원자_모집_기간이_아직_남았으면_지원이_가능하다() {
        final Project project = Project.builder()
            .applicationDuration(Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            ))
            .status(ProjectStatus.APPLYING)
            .build();

        assertThat(project.isApplicationClosed(clock)).isFalse();
    }

    @Test
    void 지원자_모집_기간이_끝나면_지원은_할_수_없다() {
        final Project project = Project.builder()
            .applicationDuration(Duration.of(
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 30)
            ))
            .build();

        assertThat(project.isApplicationClosed(clock)).isTrue();
    }

    @ParameterizedTest
    @EnumSource(value = ProjectStatus.class, names = {"PREPARING", "PROGRESSING", "COMPLETED", "CANCELLED"})
    void 프로젝트_상태가_모집_중이_아니면_지원은_할_수_없다(final ProjectStatus status) {
        final Project project = Project.builder()
            .applicationDuration(Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            ))
            .status(status)
            .build();

        assertThat(project.isApplicationClosed(clock)).isTrue();
    }
}