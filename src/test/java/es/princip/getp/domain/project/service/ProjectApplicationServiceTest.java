package es.princip.getp.domain.project.service;

import es.princip.getp.domain.client.domain.entity.Client;
import es.princip.getp.domain.client.fixture.ClientFixture;
import es.princip.getp.domain.member.domain.enums.MemberType;
import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.service.PeopleService;
import es.princip.getp.domain.project.domain.entity.Project;
import es.princip.getp.domain.project.domain.entity.ProjectApplication;
import es.princip.getp.domain.project.dto.request.ApplicateProjectRequest;
import es.princip.getp.domain.project.repository.ProjectApplicationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static es.princip.getp.domain.member.fixture.MemberFixture.createMember;
import static es.princip.getp.domain.people.fixture.PeopleFixture.createPeople;
import static es.princip.getp.fixture.ProjectApplicationFixture.createApplicateProjectRequest;
import static es.princip.getp.fixture.ProjectApplicationFixture.createProjectApplication;
import static es.princip.getp.fixture.ProjectFixture.createProject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProjectApplicationServiceTest {

    @Mock
    private ProjectApplicationRepository projectApplicationRepository;

    @Mock
    private PeopleService peopleService;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectApplicationService projectApplicationService;

    @Nested
    @DisplayName("create()는")
    class Create {

        @DisplayName("프로젝트 지원을 생성한다.")
        @Test
        void create() {
            //given
            ApplicateProjectRequest request = createApplicateProjectRequest();
            People people = createPeople(
                createMember("scv1702@princip.es", MemberType.ROLE_PEOPLE)
            );
            Client client = ClientFixture.createClient(
                createMember("knu12370@princip.es", MemberType.ROLE_CLIENT)
            );
            Project project = createProject(client);
            ProjectApplication projectApplication = createProjectApplication(people, project);

            given(peopleService.getByMemberId(eq(people.getMember().getMemberId())))
                .willReturn(people);
            given(projectService.getByProjectId(eq(project.getProjectId())))
                .willReturn(project);
            given(projectApplicationRepository.save(any(ProjectApplication.class)))
                .willReturn(projectApplication);

            //when
            ProjectApplication createdProjectApplication = projectApplicationService.create(
                people.getMember().getMemberId(), project.getProjectId(), request);

            //then
            assertEquals(projectApplication, createdProjectApplication);
        }
    }
}