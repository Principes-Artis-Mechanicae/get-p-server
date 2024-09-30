package es.princip.getp.domain.project.apply.service;

import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.exception.NotRegisteredPeopleProfileException;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.exception.ClosedProjectApplicationException;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplicationData;
import es.princip.getp.domain.project.apply.model.TeamProjectApplication;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static es.princip.getp.domain.project.commission.model.ProjectStatus.APPLYING;
import static es.princip.getp.domain.project.commission.model.ProjectStatus.PROGRESSING;
import static es.princip.getp.fixture.people.PeopleFixture.people;
import static es.princip.getp.fixture.people.PeopleFixture.peopleWithoutProfile;
import static es.princip.getp.fixture.project.AttachmentFileFixture.attachmentFiles;
import static es.princip.getp.fixture.project.ProjectApplicationFixture.DESCRIPTION;
import static es.princip.getp.fixture.project.ProjectApplicationFixture.expectedDuration;
import static es.princip.getp.fixture.project.ProjectFixture.project;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class TeamProjectApplierTest {

    private final TeamApprovalRequestSender sender = (requester, receiver, project, link) -> {};
    private final TeamProjectApplier applier = new TeamProjectApplier(sender);

    @Test
    void 피플은_팀으로_프로젝트에_지원할_수_있다() {
        final Member applicantMember = mock(Member.class);
        given(applicantMember.getId()).willReturn(new MemberId(1L));
        final People applicant = spy(people(applicantMember.getId()));
        given(applicant.getId()).willReturn(new PeopleId(1L));
        final Project project =  spy(project(new ClientId(1L), APPLYING));
        given(project.getId()).willReturn(new ProjectId(1L));
        final ProjectApplicationData data = new ProjectApplicationData(
            expectedDuration(),
            DESCRIPTION,
            attachmentFiles()
        );
        final Set<People> teammates = Set.of(
            spy(people(new MemberId(2L))),
            spy(people(new MemberId(3L)))
        );
        teammates.forEach(teammate -> {
            final Long value = teammate.getMemberId().getValue();
            given(teammate.getId()).willReturn(new PeopleId(value));
        });
        final ProjectApplication expected = TeamProjectApplication.of(
            applicant.getId(),
            project.getId(),
            data,
            teammates
        );

        final ProjectApplication application = applier.apply(applicantMember, applicant, project, data, teammates);

        assertThat(application).isInstanceOf(TeamProjectApplication.class);
        assertThat(application).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void 피플은_지원자가_프로필을_등록하지_않으면_지원할_수_없다() {
        final Member applicantMember = mock(Member.class);
        given(applicantMember.getId()).willReturn(new MemberId(1L));
        final People applicant = spy(peopleWithoutProfile(applicantMember.getId()));
        final Project project =  spy(project(new ClientId(1L), APPLYING));
        final ProjectApplicationData data = new ProjectApplicationData(
            expectedDuration(),
            DESCRIPTION,
            attachmentFiles()
        );
        final Set<People> teammates = Set.of(
            people(new MemberId(2L)),
            people(new MemberId(3L))
        );

        assertThatThrownBy(() -> applier.apply(applicantMember, applicant, project, data, teammates))
            .isInstanceOf(NotRegisteredPeopleProfileException.class);
    }

    @Test
    void 피플은_팀원이_모두_프로필을_등록하지_않으면_지원할_수_없다() {
        final Member applicantMember = mock(Member.class);
        given(applicantMember.getId()).willReturn(new MemberId(1L));
        final People applicant = spy(people(applicantMember.getId()));
        final Project project =  spy(project(new ClientId(1L), APPLYING));
        final ProjectApplicationData data = new ProjectApplicationData(
            expectedDuration(),
            DESCRIPTION,
            attachmentFiles()
        );
        final Set<People> teammates = Set.of(
            people(new MemberId(2L)),
            peopleWithoutProfile(new MemberId(3L))
        );

        assertThatThrownBy(() -> applier.apply(applicantMember, applicant, project, data, teammates))
            .isInstanceOf(NotRegisteredPeopleProfileException.class);
    }

    @Test
    void 프로젝트_지원이_닫힌_경우_지원할_수_없다() {
        final Member applicantMember = mock(Member.class);
        given(applicantMember.getId()).willReturn(new MemberId(1L));
        final People applicant = spy(people(applicantMember.getId()));
        final Project project =  spy(project(new ClientId(1L), PROGRESSING));
        final ProjectApplicationData data = new ProjectApplicationData(
            expectedDuration(),
            DESCRIPTION,
            attachmentFiles()
        );
        final Set<People> teammates = Set.of(
            people(new MemberId(2L)),
            people(new MemberId(3L))
        );

        assertThatThrownBy(() -> applier.apply(applicantMember, applicant, project, data, teammates))
            .isInstanceOf(ClosedProjectApplicationException.class);
    }
}