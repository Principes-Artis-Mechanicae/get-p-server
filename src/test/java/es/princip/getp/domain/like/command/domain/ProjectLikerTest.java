package es.princip.getp.domain.like.command.domain;

import es.princip.getp.domain.like.command.domain.project.ProjectLike;
import es.princip.getp.domain.like.command.domain.project.ProjectLikeRepository;
import es.princip.getp.domain.like.command.domain.project.ProjectLiker;
import es.princip.getp.domain.like.exception.AlreadyLikedException;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.project.command.domain.Project;
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

    private final Likeable people = mock(People.class);
    private final LikeReceivable project = mock(Project.class);

    @BeforeEach
    void setUp() {
        given(people.getId()).willReturn(peopleId);
        given(project.getId()).willReturn(projectId);
    }

    @Test
    void 피플은_프로젝트에_좋아요를_누를_수_있다() {
        given(projectLikeRepository.existsByLikerIdAndLikedId(peopleId, projectId))
            .willReturn(false);

        ProjectLike like = projectLiker.like(people, project);

        assertThat(like).isNotNull();
    }

    @Test
    void 피플은_프로젝트에_좋아요를_중복으로_누를_수_없다() {
        given(projectLikeRepository.existsByLikerIdAndLikedId(peopleId, projectId))
            .willReturn(true);

        assertThatThrownBy(() -> projectLiker.like(people, project))
            .isInstanceOf(AlreadyLikedException.class);
    }
}