package es.princip.getp.domain.project.confirmation.service;

import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplicationStatus;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.domain.project.confirmation.exception.NotCompletedProjectMeetingException;
import es.princip.getp.domain.project.confirmation.model.ProjectConfirmation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static es.princip.getp.domain.project.commission.model.ProjectStatus.APPLICATION_OPENED;
import static es.princip.getp.fixture.people.PeopleFixture.people;
import static es.princip.getp.fixture.project.ProjectApplicationFixture.individualProjectApplication;
import static es.princip.getp.fixture.project.ProjectFixture.project;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class ProjectConfirmerTest {

    private final ProjectConfirmer projectConfirmer = new ProjectConfirmer();
    private final ClientId clientId = new ClientId(1L);

    @Test
    void 지원자는_미팅완료를_하면_확정_지원자가_될_수_있다() {
        final People applicant = spy(people(new MemberId(1L)));
        given(applicant.getId()).willReturn(new PeopleId(1L));
        final Project project = spy(project(new ClientId(1L), APPLICATION_OPENED));
        given(project.getId()).willReturn(new ProjectId(1L));
        final ProjectApplication application = spy(individualProjectApplication(
            applicant.getId(), project.getId(), ProjectApplicationStatus.MEETING_COMPLETED
        ));

        ProjectConfirmation projectConfirmation = projectConfirmer.confirm(project, applicant, application);

        assertThat(projectConfirmation).isNotNull();
    }

    @Test
    void 지원자는_미팅완료를_하지않으면_확정_지원자가_될_수_없다() {
        final People applicant = spy(people(new MemberId(1L)));
        given(applicant.getId()).willReturn(new PeopleId(1L));
        final Project project = spy(project(new ClientId(1L), APPLICATION_OPENED));
        given(project.getId()).willReturn(new ProjectId(1L));
        final ProjectApplication application = spy(individualProjectApplication(
                applicant.getId(), project.getId(), ProjectApplicationStatus.COMPLETED
        ));

        Assertions.assertThatThrownBy(() -> projectConfirmer.confirm(project, applicant, application))
            .isInstanceOf(NotCompletedProjectMeetingException.class);
    }
}