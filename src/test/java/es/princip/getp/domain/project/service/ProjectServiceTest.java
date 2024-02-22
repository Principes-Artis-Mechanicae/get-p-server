package es.princip.getp.domain.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import es.princip.getp.domain.client.entity.Client;
import es.princip.getp.domain.client.service.ClientService;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.project.dto.request.CreateProjectRequest;
import es.princip.getp.domain.project.entity.Project;
import es.princip.getp.domain.project.repository.ProjectRepository;
import es.princip.getp.fixture.ClientFixture;
import es.princip.getp.fixture.MemberFixture;
import es.princip.getp.fixture.ProjectFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
