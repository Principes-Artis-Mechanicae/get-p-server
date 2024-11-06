package es.princip.getp.application.like.project.service;

import es.princip.getp.application.like.exception.NeverLikedException;
import es.princip.getp.application.like.project.port.out.CheckProjectLikePort;
import es.princip.getp.application.like.project.port.out.DeleteProjectLikePort;
import es.princip.getp.application.like.project.port.out.LoadProjectLikePort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.domain.client.model.ClientId;
import es.princip.getp.domain.like.project.model.ProjectLike;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import es.princip.getp.fixture.like.ProjectLikeFixture;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UnlikeProjectServiceTest {

    @Mock private LoadProjectPort loadProjectPort;
    @Mock private LoadProjectLikePort loadProjectLikePort;
    @Mock private CheckProjectLikePort checkProjectLikePort;
    @Mock private DeleteProjectLikePort deleteProjectLikePort;
    @InjectMocks private UnlikeProjectService unlikeProjectService;

    private final MemberId memberId = new MemberId(1L);
    private final ClientId clientId = new ClientId(1L);
    private final ProjectId projectId = new ProjectId(1L);

    private final Project project = ProjectFixture.project(clientId, ProjectStatus.APPLICATION_OPENED);
    private final ProjectLike like = ProjectLikeFixture.projectLike(memberId, projectId);

    @BeforeEach
    void setUp() {
        given(loadProjectPort.loadBy(projectId)).willReturn(project);
    }

    @Test
    void 피플은_프로젝트에_눌렀던_좋아요를_취소할_수_있다() {
        mockMemberAlreadyLikedProject(checkProjectLikePort, memberId, projectId);
        given(loadProjectLikePort.loadBy(memberId, projectId))
            .willReturn(like);

        unlikeProjectService.unlike(memberId, projectId);

        verify(deleteProjectLikePort, times(1)).delete(like);
    }

    @Test
    void 피플은_좋아요를_누른적이_없는_프로젝트에_좋아요를_취소할_수_없다() {
        mockMemberNeverLikedProject(checkProjectLikePort, memberId, projectId);

        assertThatThrownBy(() -> unlikeProjectService.unlike(memberId, projectId))
            .isInstanceOf(NeverLikedException.class);
    }
}
