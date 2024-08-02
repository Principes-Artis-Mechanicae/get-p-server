package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.project.exception.ProjectAlreadyLikedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProjectLikerTest {

    @Mock
    private ProjectLikeRepository projectLikeRepository;

    @InjectMocks
    private ProjectLiker projectLiker;

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
    void 피플은_프로젝트에_좋아요를_누를_수_있다() {
        given(projectLikeRepository.existsByPeopleIdAndProjectId(peopleId, projectId))
            .willReturn(false);

        ProjectLike like = projectLiker.like(people, project);

        assertThat(like).isNotNull();
    }

    @Test
    void 피플은_프로젝트에_좋아요를_중복으로_누를_수_없다() {
        given(projectLikeRepository.existsByPeopleIdAndProjectId(peopleId, projectId))
            .willReturn(true);

        assertThatThrownBy(() -> projectLiker.like(people, project))
            .isInstanceOf(ProjectAlreadyLikedException.class);
    }
}