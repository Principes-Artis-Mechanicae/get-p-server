package es.princip.getp.domain.project.commission.model;

import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.common.model.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static es.princip.getp.domain.project.commission.model.ProjectStatus.APPLICATION_OPENED;
import static es.princip.getp.fixture.project.ProjectFixture.project;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("프로젝트")
@ExtendWith(MockitoExtension.class)
class ProjectTest {

    @Nested
    class 지원이_가능한지_확인한다 {

        private final ClientId clientId = new ClientId(1L);

        @ParameterizedTest
        @EnumSource(value = ProjectStatus.class, names = {"PREPARING", "PROGRESSING", "COMPLETED", "CANCELLED"})
        void 프로젝트_상태가_모집_중이_아니면_지원은_할_수_없다(final ProjectStatus status) {
            final Project project = project(
                clientId,
                status,
                Duration.of(
                    LocalDate.of(2024, 7, 1),
                    LocalDate.of(2024, 7, 31)
                )
            );

            assertThat(project.isApplicationClosed()).isTrue();
        }
    }

    @Nested
    class 주어진_의뢰자가_프로젝트의_의뢰자인지_확인한다 {

        private final ClientId clientId = new ClientId(1L);
        private final Project project = project(clientId, APPLICATION_OPENED);
        private final Client client = mock(Client.class);

        @Test
        void 주어진_의뢰자와_의뢰자_ID가_같으면_프로젝트의_의뢰자다() {
            given(client.getId()).willReturn(clientId);

            assertThat(project.isClient(client)).isTrue();
        }

        @Test
        void 주어진_의뢰자와_의뢰자_ID가_다르면_프로젝트의_의뢰자가_아니다() {
            given(client.getId()).willReturn(new ClientId(2L));

            assertThat(project.isClient(client)).isFalse();
        }
    }
}