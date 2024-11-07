package es.princip.getp.domain.project.apply.model;

import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static es.princip.getp.domain.project.apply.model.ProjectApplicationStatus.PENDING_TEAM_APPROVAL;
import static es.princip.getp.fixture.project.ProjectApplicationFixture.teamProjectApplication;
import static es.princip.getp.fixture.project.ProjectApplicationFixture.teammate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TeamProjectApplicationTest {

    @Test
    void 피플은_팀원_신청을_승인할_수_있다() {
        final PeopleId applicantId = new PeopleId(1L);
        final ProjectId projectId = new ProjectId(1L);
        final People teammate = mock(People.class);
        final PeopleId teammateId = new PeopleId(2L);
        given(teammate.getId()).willReturn(teammateId);
        final Set<Teammate> teammates = Set.of(
            teammate(teammateId, TeammateStatus.PENDING),
            teammate(new PeopleId(3L), TeammateStatus.PENDING)
        );
        final TeamProjectApplication application = teamProjectApplication(
            applicantId,
            projectId,
            PENDING_TEAM_APPROVAL,
            teammates
        );

        application.approve(teammate);

        assertThat(application.isTeammateApproved(teammateId)).isTrue();
        assertThat(application.isCompleted()).isFalse();
    }

    @Test
    void 팀원이_모두_승인해야_프로젝트_지원이_완료된다() {
        final PeopleId applicantId = new PeopleId(1L);
        final ProjectId projectId = new ProjectId(1L);
        final People teammate = mock(People.class);
        final PeopleId teammateId = new PeopleId(2L);
        given(teammate.getId()).willReturn(teammateId);
        final Set<Teammate> teammates = Set.of(
            teammate(teammateId, TeammateStatus.PENDING)
        );
        final TeamProjectApplication application = teamProjectApplication(
            applicantId,
            projectId,
            PENDING_TEAM_APPROVAL,
            teammates
        );

        application.approve(teammate);

        assertThat(application.isCompleted()).isTrue();
    }

    @ParameterizedTest
    @EnumSource(mode = EXCLUDE, names = {"PENDING_TEAM_APPROVAL"})
    void 팀원_승인_대기_상태가_아니면_승인할_수_없다(final ProjectApplicationStatus status) {
        final PeopleId applicantId = new PeopleId(1L);
        final ProjectId projectId = new ProjectId(1L);
        final People teammate = mock(People.class);
        final PeopleId teammateId = new PeopleId(2L);
        final Set<Teammate> teammates = Set.of(
            teammate(teammateId, TeammateStatus.PENDING)
        );
        final TeamProjectApplication application = teamProjectApplication(
            applicantId,
            projectId,
            status,
            teammates
        );

        assertThatThrownBy(() -> application.approve(teammate))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 팀원_승인_신청을_받지_않으면_승인할_수_없다() {
        final PeopleId applicantId = new PeopleId(1L);
        final ProjectId projectId = new ProjectId(1L);
        final People teammate = mock(People.class);
        final PeopleId teammateId = new PeopleId(2L);
        given(teammate.getId()).willReturn(teammateId);
        final Set<Teammate> teammates = Set.of(
            teammate(new PeopleId(3L), TeammateStatus.PENDING)
        );
        final TeamProjectApplication application = teamProjectApplication(
            applicantId,
            projectId,
            PENDING_TEAM_APPROVAL,
            teammates
        );

        assertThatThrownBy(() -> application.approve(teammate))
            .isInstanceOf(IllegalArgumentException.class);
    }
}