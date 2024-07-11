package es.princip.getp.domain.project.application;

import es.princip.getp.domain.client.application.ClientService;
import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.client.fixture.ClientFixture;
import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.member.fixture.MemberFixture;
import es.princip.getp.domain.project.domain.Project;
import es.princip.getp.domain.project.dto.request.CreateProjectRequest;
import es.princip.getp.domain.project.fixture.ProjectFixture;
import es.princip.getp.domain.project.repository.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ClientService clientService;

    @Mock
    private ProjectRepository ProjectRepository;

    @DisplayName("create()는")
    @Nested
    class Create {

        @DisplayName("프로젝트를 생성한다.")
        @Test
        void create() {
            //given
            CreateProjectRequest request = ProjectFixture.createProjectRequest();
            Member member = MemberFixture.createMember();
            Client client = ClientFixture.createClient(member);
            Project project = ProjectFixture.createProject(client);

            given(clientService.getByMemberId(eq(member.getMemberId()), any())).willReturn(client);
            given(ProjectRepository.save(any(Project.class))).willReturn(project);

            //when
            Project createdProject = projectService.create(member.getMemberId(), request);

            //then
            assertEquals(project, createdProject);
        }
    }
}
