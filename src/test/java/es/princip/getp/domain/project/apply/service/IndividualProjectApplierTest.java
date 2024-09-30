package es.princip.getp.domain.project.apply.service;

import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.exception.NotRegisteredPeopleProfileException;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.exception.ClosedProjectApplicationException;
import es.princip.getp.domain.project.apply.model.IndividualProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplicationData;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

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
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class IndividualProjectApplierTest {

    private final IndividualProjectApplier applier = new IndividualProjectApplier();

    @Test
    void 피플은_개인으로_프로젝트에_지원할_수_있다() {
        final People applicant = spy(people(new MemberId(1L)));
        given(applicant.getId()).willReturn(new PeopleId(1L));
        final Project project =  spy(project(new ClientId(1L), APPLYING));
        given(project.getId()).willReturn(new ProjectId(1L));
        final ProjectApplicationData data = new ProjectApplicationData(
            expectedDuration(),
            DESCRIPTION,
            attachmentFiles()
        );
        final ProjectApplication expected = IndividualProjectApplication.of(
            applicant.getId(),
            project.getId(),
            data
        );

        final ProjectApplication application = applier.apply(applicant, project, data);

        assertThat(application).isInstanceOf(IndividualProjectApplication.class);
        assertThat(application).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void 피플은_프로필을_등록하지_않으면_지원할_수_없다() {
        final People applicant = spy(peopleWithoutProfile(new MemberId(1L)));
        final Project project =  spy(project(new ClientId(1L), APPLYING));
        final ProjectApplicationData data = new ProjectApplicationData(
            expectedDuration(),
            DESCRIPTION,
            attachmentFiles()
        );

        assertThatThrownBy(() -> applier.apply(applicant, project, data))
            .isInstanceOf(NotRegisteredPeopleProfileException.class);
    }

    @Test
    void 프로젝트_지원이_닫힌_경우_지원할_수_없다() {
        final People applicant = spy(people(new MemberId(1L)));
        final Project project =  spy(project(new ClientId(1L), PROGRESSING));
        final ProjectApplicationData data = new ProjectApplicationData(
            expectedDuration(),
            DESCRIPTION,
            attachmentFiles()
        );

        assertThatThrownBy(() -> applier.apply(applicant, project, data))
            .isInstanceOf(ClosedProjectApplicationException.class);
    }
}