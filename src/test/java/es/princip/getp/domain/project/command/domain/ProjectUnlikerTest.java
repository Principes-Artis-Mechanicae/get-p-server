package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.project.exception.ProjectNeverLikedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProjectUnlikerTest {

    @Mock
    private ProjectLikeRepository projectLikeRepository;

    @InjectMocks
    private ProjectUnliker projectUnliker;

    private final Long peopleId = 1L;
    private final Long projectId = 1L;

    private final People people = mock(People.class);
    private final Project project = mock(Project.class);

    @BeforeEach
    void setUp() {
        given(people.getPeopleId()).willReturn(peopleId);
        given(project.getProjectId()).willReturn(projectId);
    }

    @Test
    void 피플은_프로젝트에_눌렀던_좋아요를_취소할_수_있다() {
        given(projectLikeRepository.existsByPeopleIdAndProjectId(peopleId, projectId))
            .willReturn(true);

        assertThatCode(() -> projectUnliker.unlike(people, project))
            .doesNotThrowAnyException();
    }

    @Test
    void 피플은_좋아요를_누른적이_없는_프로젝트에_좋아요를_취소할_수_없다() {
        given(projectLikeRepository.existsByPeopleIdAndProjectId(peopleId, projectId))
            .willReturn(false);

        assertThatThrownBy(() -> projectUnliker.unlike(people, project))
            .isInstanceOf(ProjectNeverLikedException.class);
    }
}