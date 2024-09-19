package es.princip.getp.application.like.project.service;

import es.princip.getp.application.like.exception.AlreadyLikedException;
import es.princip.getp.application.like.project.port.out.CheckProjectLikePort;
import es.princip.getp.application.like.project.port.out.SaveProjectLikePort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import es.princip.getp.fixture.project.ProjectFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static es.princip.getp.application.like.project.service.LikeProjectMockingUtil.mockMemberAlreadyLikedProject;
import static es.princip.getp.application.like.project.service.LikeProjectMockingUtil.mockMemberNeverLikedProject;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LikeProjectServiceTest {

    @Mock private LoadProjectPort loadProjectPort;
    @Mock private CheckProjectLikePort checkProjectLikePort;
    @Mock private SaveProjectLikePort saveProjectLikePort;
    @InjectMocks private LikeProjectService likeProjectService;

    private final MemberId memberId = new MemberId(1L);
    private final ClientId clientId = new ClientId(1L);
    private final ProjectId projectId = new ProjectId(1L);
    private final Project project = ProjectFixture.project(clientId, ProjectStatus.APPLYING);

    @BeforeEach
    void setUp() {
        given(loadProjectPort.loadBy(projectId)).willReturn(project);
    }

    @Test
    void 피플은_프로젝트에_좋아요를_누를_수_있다() {
        mockMemberNeverLikedProject(checkProjectLikePort, memberId, projectId);

        likeProjectService.like(memberId, projectId);

        verify(saveProjectLikePort, times(1)).save(
            argThat(
                arg -> arg.getMemberId().equals(memberId) && arg.getProjectId().equals(projectId)
            )
        );
    }

    @Test
    void 피플은_프로젝트에_좋아요를_중복으로_누를_수_없다() {
        mockMemberAlreadyLikedProject(checkProjectLikePort, memberId, projectId);

        assertThatThrownBy(() -> likeProjectService.like(memberId, projectId))
            .isInstanceOf(AlreadyLikedException.class);
    }
}
