package es.princip.getp.domain.project.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

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
            CreateProjectRequest request = ProjectFixture.createProjectRequest();
            Member member = MemberFixture.createMember();
            Client client = ClientFixture.createClientByMember(member);
            Project project = request.toEntity(client);
            when(clientService.getByMemberId(eq(member.getMemberId()), any())).thenReturn(client);
            when(ProjectRepository.save(any(Project.class))).thenReturn(project);

            Project createdProject = projectService.create(member.getMemberId(), request);

            assertSoftly(softly -> {
                softly.assertThat(project.getTitle()).isEqualTo(createdProject.getTitle());
                softly.assertThat(project.getPayment()).isEqualTo(createdProject.getPayment());
                softly.assertThat(project.getMeetingType())
                    .isEqualTo(createdProject.getMeetingType());
                softly.assertThat(project.getDescription())
                    .isEqualTo(createdProject.getDescription());
                softly.assertThat(project.getEstimatedDuration())
                    .isEqualTo(createdProject.getEstimatedDuration());
                softly.assertThat(project.getApplicationDeadline())
                    .isEqualTo(createdProject.getApplicationDeadline());
                softly.assertThat(project.getHashtags()).isEqualTo(createdProject.getHashtags());
                softly.assertThat(project.getAttachmentFiles())
                    .isEqualTo(createdProject.getAttachmentFiles());
            });
        }
    }
}
