package es.princip.getp.application.like.project.service;

import es.princip.getp.application.like.exception.NeverLikedException;
import es.princip.getp.application.like.project.port.out.CheckProjectLikePort;
import es.princip.getp.application.like.project.port.out.DeleteProjectLikePort;
import es.princip.getp.application.like.project.port.out.LoadProjectLikePort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.domain.like.project.model.ProjectLike;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.people.model.PeopleType;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import es.princip.getp.fixture.like.ProjectLikeFixture;
import es.princip.getp.fixture.people.PeopleFixture;
import es.princip.getp.fixture.project.ProjectFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnlikeProjectServiceTest {

    @Mock private LoadPeoplePort loadPeoplePort;
    @Mock private LoadProjectPort loadProjectPort;
    @Mock private LoadProjectLikePort loadProjectLikePort;
    @Mock private CheckProjectLikePort checkProjectLikePort;
    @Mock private DeleteProjectLikePort deleteProjectLikePort;
    @InjectMocks private UnlikeProjectService unlikeProjectService;

    private final MemberId memberId = new MemberId(1L);
    private final PeopleId peopleId = new PeopleId(1L);
    private final Long clientId = 1L;
    private final Long projectId = 1L;
    private final Project project = ProjectFixture.project(clientId, ProjectStatus.APPLYING);
    private final ProjectLike like = ProjectLikeFixture.projectLike(peopleId, projectId);

    @BeforeEach
    void setUp() {
        given(loadProjectPort.loadBy(projectId)).willReturn(project);
        final People people = spy(PeopleFixture.people(memberId, PeopleType.INDIVIDUAL));
        doReturn(peopleId).when(people).getId();
        given(loadPeoplePort.loadBy(memberId)).willReturn(people);
    }

    @Test
    void 피플은_프로젝트에_눌렀던_좋아요를_취소할_수_있다() {
        given(checkProjectLikePort.existsBy(peopleId, projectId))
            .willReturn(true);
        given(loadProjectLikePort.loadBy(peopleId, projectId))
            .willReturn(like);

        unlikeProjectService.unlike(memberId, projectId);

        verify(deleteProjectLikePort, times(1)).delete(like);
    }

    @Test
    void 피플은_좋아요를_누른적이_없는_프로젝트에_좋아요를_취소할_수_없다() {
        given(checkProjectLikePort.existsBy(peopleId, projectId))
            .willReturn(false);

        assertThatThrownBy(() -> unlikeProjectService.unlike(memberId, projectId))
            .isInstanceOf(NeverLikedException.class);
    }
}
