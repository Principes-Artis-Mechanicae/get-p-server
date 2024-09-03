package es.princip.getp.domain.like.command.domain;

import es.princip.getp.domain.like.command.domain.project.ProjectLikeRepository;
import es.princip.getp.domain.like.command.domain.project.ProjectUnliker;
import es.princip.getp.domain.like.exception.NeverLikedException;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.project.commission.model.Project;
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

    private final Likeable people = mock(People.class);
    private final LikeReceivable project = mock(Project.class);

    @BeforeEach
    void setUp() {
        given(people.getId()).willReturn(peopleId);
        given(project.getId()).willReturn(projectId);
    }

    @Test
    void 피플은_프로젝트에_눌렀던_좋아요를_취소할_수_있다() {
        given(projectLikeRepository.existsByLikerIdAndLikedId(peopleId, projectId))
            .willReturn(true);

        assertThatCode(() -> projectUnliker.unlike(people, project))
            .doesNotThrowAnyException();
    }

    @Test
    void 피플은_좋아요를_누른적이_없는_프로젝트에_좋아요를_취소할_수_없다() {
        given(projectLikeRepository.existsByLikerIdAndLikedId(peopleId, projectId))
            .willReturn(false);

        assertThatThrownBy(() -> projectUnliker.unlike(people, project))
            .isInstanceOf(NeverLikedException.class);
    }
}