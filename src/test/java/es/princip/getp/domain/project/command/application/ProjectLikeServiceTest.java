package es.princip.getp.domain.project.command.application;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.project.command.domain.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectLikeServiceTest {

    @InjectMocks
    private ProjectLikeService projectLikeService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private PeopleRepository peopleRepository;

    @Nested
    @DisplayName("like()는")
    class Like {

        private final Long memberId = 1L;
        private final Long projectId = 1L;

        @DisplayName("프로젝트에 좋아요를 누른다.")
        @Test
        void like() {
            final People people = spy(People.class);
            given(projectRepository.existsById(projectId)).willReturn(true);
            given(peopleRepository.findByMemberId(memberId)).willReturn(Optional.of(people));

            projectLikeService.like(memberId, projectId);

            verify(people, times(1)).likeProject(projectId);
        }
    }
}