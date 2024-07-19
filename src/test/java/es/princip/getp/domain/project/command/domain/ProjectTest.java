package es.princip.getp.domain.project.command.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("프로젝트")
class ProjectTest {

    final LocalDate now = LocalDate.now();

    @Test
    void 지원자_모집_기간이_아직_남았으면_지원이_가능하다() {
        final Project project = Project.builder()
            .applicationDuration(ApplicationDuration.of(
                now.minusDays(2),
                now.plusDays(2)
            ))
            .status(ProjectStatus.APPLYING)
            .build();

        assertThat(project.isApplicationClosed()).isFalse();
    }

    @Test
    void 지원자_모집_기간이_끝나면_지원은_할_수_없다() {
        final LocalDate now = LocalDate.now();
        final Project project = Project.builder()
            .applicationDuration(ApplicationDuration.of(
                now.minusDays(2),
                now
            ))
            .build();

        assertThat(project.isApplicationClosed()).isTrue();
    }

    @Test
    void 프로젝트가_진행_중이면_지원은_할_수_없다() {
        final Project project = Project.builder()
            .applicationDuration(ApplicationDuration.of(
                now.minusDays(2),
                now
            ))
            .status(ProjectStatus.PROGRESSING)
            .build();

        assertThat(project.isApplicationClosed()).isTrue();
    }

    @Test
    void 프로젝트가_완수되면_지원은_할_수_없다() {
        final Project project = Project.builder()
            .applicationDuration(ApplicationDuration.of(
                now.minusDays(2),
                now
            ))
            .status(ProjectStatus.COMPLETED)
            .build();

        assertThat(project.isApplicationClosed()).isTrue();
    }

    @Test
    void 프로젝트가_취소되면_지원은_할_수_없다() {
        final Project project = Project.builder()
            .applicationDuration(ApplicationDuration.of(
                now.minusDays(2),
                now
            ))
            .status(ProjectStatus.CANCELLED)
            .build();

        assertThat(project.isApplicationClosed()).isTrue();
    }
}