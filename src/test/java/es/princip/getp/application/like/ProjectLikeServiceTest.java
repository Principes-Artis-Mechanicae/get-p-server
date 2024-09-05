package es.princip.getp.application.like;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.princip.getp.application.like.exception.AlreadyLikedException;
import es.princip.getp.application.like.exception.NeverLikedException;
import es.princip.getp.application.like.port.out.project.CheckProjectLikePort;
import es.princip.getp.application.like.port.out.project.LoadProjectLikePort;
import es.princip.getp.application.like.port.out.project.ProjectLikePort;
import es.princip.getp.application.like.port.out.project.ProjectUnlikePort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.domain.like.model.project.ProjectLike;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleType;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import es.princip.getp.fixture.like.ProjectLikeFixture;
import es.princip.getp.fixture.people.PeopleFixture;
import es.princip.getp.fixture.project.ProjectFixture;

@ExtendWith(MockitoExtension.class)
public class ProjectLikeServiceTest {
    
    @Mock private ProjectLikePort projectLikePort;

    @Mock private ProjectUnlikePort projectUnlikePort;

    @Mock private LoadProjectLikePort loadProjectLikePort;

    @Mock private CheckProjectLikePort checkProjectLikePort;

    @Mock private LoadPeoplePort loadPeoplePort;

    @Mock private LoadProjectPort loadProjectPort;

    @InjectMocks private ProjectLikeService projectLikeService;

    private final Long memberId = 1L;
    private final Long peopleId = 1L;
    private final Long clientId = 1L;
    private final Long projectId = 1L;
    private final Project project = ProjectFixture.project(clientId, ProjectStatus.APPLYING);
    private final ProjectLike like = ProjectLikeFixture.projectLike(peopleId, projectId);

    @BeforeEach
    void setUp() {
        given(loadProjectPort.loadBy(projectId)).willReturn(project);
        People people = spy(PeopleFixture.people(memberId, PeopleType.INDIVIDUAL));
        doReturn(peopleId).when(people).getId();
        given(loadPeoplePort.loadBy(memberId)).willReturn(people);
    }
    //TODO : 해결
    @Test
    void 피플은_프로젝트에_좋아요를_누를_수_있다() {
        given(checkProjectLikePort.existsByPeopleIdAndProjectId(peopleId, projectId))
            .willReturn(false);

        projectLikeService.like(memberId, projectId);

        verify(projectLikePort, times(1)).like(
            argThat(
                arg -> arg.getPeopleId().equals(peopleId) && arg.getProjectId().equals(projectId)
            )
        );
    }

    @Test
    void 피플은_프로젝트에_좋아요를_중복으로_누를_수_없다() {
        given(checkProjectLikePort.existsByPeopleIdAndProjectId(peopleId, projectId))
            .willReturn(true);

        assertThatThrownBy(() -> projectLikeService.like(memberId, projectId))
            .isInstanceOf(AlreadyLikedException.class);
    }

    @Test
    void 피플은_프로젝트에_눌렀던_좋아요를_취소할_수_있다() {
        given(checkProjectLikePort.existsByPeopleIdAndProjectId(peopleId, projectId))
            .willReturn(true);
        given(loadProjectLikePort.findByPeopleIdAndProjectId(peopleId, projectId))
            .willReturn(like);
        
        projectLikeService.unlike(memberId, projectId);

        verify(projectUnlikePort, times(1)).unlike(like);
    }

    @Test
    void 피플은_좋아요를_누른적이_없는_프로젝트에_좋아요를_취소할_수_없다() {
        given(checkProjectLikePort.existsByPeopleIdAndProjectId(peopleId, projectId))
            .willReturn(false);

        assertThatThrownBy(() -> projectLikeService.unlike(memberId, projectId))
            .isInstanceOf(NeverLikedException.class);
    }
}
