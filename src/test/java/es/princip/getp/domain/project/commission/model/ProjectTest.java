package es.princip.getp.domain.project.commission.model;

import es.princip.getp.common.infra.StubClockHolder;
import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.common.model.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDate;

import static es.princip.getp.domain.project.commission.model.ProjectStatus.APPLYING;
import static es.princip.getp.fixture.project.ProjectFixture.project;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("프로젝트")
@ExtendWith(MockitoExtension.class)
class ProjectTest {

    @Nested
    class 지원이_가능한지_확인한다 {

        private final LocalDate now = LocalDate.of(2024, 7, 1);
        private final Clock clock = new StubClockHolder(now).getClock();

        @Test
        void 지원자_모집_기간이_아직_남았으면_지원이_가능하다() {
            final Project project = project(
                1L,
                APPLYING,
                Duration.of(
                    LocalDate.of(2024, 7, 1),
                    LocalDate.of(2024, 7, 31)
                )
            );

            assertThat(project.isApplicationClosed(clock)).isFalse();
        }

        @Test
        void 지원자_모집_기간이_끝나면_지원은_할_수_없다() {
            final Project project = project(
                1L,
                APPLYING,
                Duration.of(
                    LocalDate.of(2024, 6, 1),
                    LocalDate.of(2024, 6, 30)
                )
            );

            assertThat(project.isApplicationClosed(clock)).isTrue();
        }

        @ParameterizedTest
        @EnumSource(value = ProjectStatus.class, names = {"PREPARING", "PROGRESSING", "COMPLETED", "CANCELLED"})
        void 프로젝트_상태가_모집_중이_아니면_지원은_할_수_없다(final ProjectStatus status) {
            final Project project = project(
                1L,
                status,
                Duration.of(
                    LocalDate.of(2024, 7, 1),
                    LocalDate.of(2024, 7, 31)
                )
            );

            assertThat(project.isApplicationClosed(clock)).isTrue();
        }
    }

    @Nested
    class 주어진_의뢰자가_프로젝트의_의뢰자인지_확인한다 {

        private final Project project = project(1L, APPLYING);
        private final Client client = mock(Client.class);

        @Test
        void 주어진_의뢰자와_의뢰자_ID가_같으면_프로젝트의_의뢰자다() {
            given(client.getClientId()).willReturn(1L);

            assertThat(project.isClient(client)).isTrue();
        }

        @Test
        void 주어진_의뢰자와_의뢰자_ID가_다르면_프로젝트의_의뢰자가_아니다() {
            given(client.getClientId()).willReturn(2L);

            assertThat(project.isClient(client)).isFalse();
        }
    }
}