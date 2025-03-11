package es.princip.getp.application.like.project.service;

import es.princip.getp.application.like.exception.AlreadyLikedException;
import es.princip.getp.application.like.project.port.out.CheckProjectLikePort;
import es.princip.getp.application.like.project.port.out.SaveProjectLikePort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleType;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import es.princip.getp.fixture.people.PeopleFixture;
import es.princip.getp.fixture.project.ProjectFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LikeProjectServiceTest {

    @Mock private LoadPeoplePort loadPeoplePort;
    @Mock private LoadProjectPort loadProjectPort;
    @Mock private CheckProjectLikePort checkProjectLikePort;
    @Mock private SaveProjectLikePort saveProjectLikePort;
    @InjectMocks private LikeProjectService likeProjectService;

    private final Long memberId = 1L;
    private final Long peopleId = 1L;
    private final Long clientId = 1L;
    private final Long projectId = 1L;
    private final Project project = ProjectFixture.project(clientId, ProjectStatus.APPLYING);

    @BeforeEach
    void setUp() {
        given(loadProjectPort.loadBy(projectId)).willReturn(project);
        final People people = spy(PeopleFixture.people(memberId, PeopleType.INDIVIDUAL));
        doReturn(peopleId).when(people).getId();
        given(loadPeoplePort.loadBy(memberId)).willReturn(people);
    }

    @Test
    void 피플은_프로젝트에_좋아요를_누를_수_있다() {
        given(checkProjectLikePort.existsBy(peopleId, projectId))
            .willReturn(false);

        likeProjectService.like(memberId, projectId);

        verify(saveProjectLikePort, times(1)).save(
            argThat(
                arg -> arg.getPeopleId().equals(peopleId) && arg.getProjectId().equals(projectId)
            )
        );
    }

    @Test
    void 피플은_프로젝트에_좋아요를_중복으로_누를_수_없다() {
        given(checkProjectLikePort.existsBy(peopleId, projectId))
            .willReturn(true);

        assertThatThrownBy(() -> likeProjectService.like(memberId, projectId))
            .isInstanceOf(AlreadyLikedException.class);
    }
}
