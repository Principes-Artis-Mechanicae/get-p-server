package es.princip.getp.domain.project.application;

import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.people.application.PeopleService;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.project.domain.Project;
import es.princip.getp.domain.project.domain.ProjectLikeRepository;
import es.princip.getp.domain.project.exception.ProjectErrorCode;
import es.princip.getp.domain.project.exception.ProjectLikeErrorCode;
import es.princip.getp.infra.exception.BusinessLogicException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static es.princip.getp.domain.member.fixture.MemberFixture.createMember;
import static es.princip.getp.domain.people.fixture.PeopleFixture.createPeople;
import static es.princip.getp.domain.project.fixture.ProjectFixture.createProject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProjectLikeServiceTest {

    @InjectMocks
    private ProjectLikeService projectLikeService;

    @Mock
    private ProjectService projectService;

    @Mock
    private PeopleService peopleService;

    @Mock
    private ProjectLikeRepository projectLikeRepository;

    @Nested
    @DisplayName("like()는")
    class Like {

        private final Long memberId = 1L;
        private final Long projectId = 1L;
        private final Member member = createMember();

        private final People people = createPeople(member);
        private final Project project = createProject();

        @DisplayName("프로젝트에 좋아요를 누른다.")
        @Test
        void like() {
            given(projectService.getByProjectId(projectId)).willReturn(project);
            given(peopleService.getByMemberId(memberId)).willReturn(people);
            given(projectLikeRepository.existsByPeople_PeopleIdAndProject_ProjectId(
                any(), any()
            )).willReturn(false);

            projectLikeService.like(memberId, projectId);

            verify(projectLikeRepository, times(1)).save(any());
        }

        @DisplayName("프로젝트에 이미 좋아요가 눌러진 경우 예외를 던진다.")
        @Test
        void like_WhenProjectIsAlreadyLiked_ShouldThrowException() {
            given(projectService.getByProjectId(projectId)).willReturn(project);
            given(peopleService.getByMemberId(memberId)).willReturn(people);
            given(projectLikeRepository.existsByPeople_PeopleIdAndProject_ProjectId(
                any(), any()
            )).willReturn(true);

            BusinessLogicException exception =
                catchThrowableOfType(() -> projectLikeService.like(memberId, projectId),
                    BusinessLogicException.class);
            assertThat(exception.getErrorCode()).isEqualTo(ProjectLikeErrorCode.ALREADY_LIKED);
        }

        @DisplayName("좋아요를 누를 프로젝트가 존재하지 않는 경우 예외를 던진다.")
        @Test
        void like_WhenProjectIsNotFound_ShouldThrowException() {
            given(peopleService.getByMemberId(memberId))
                .willReturn(people);
            given(projectService.getByProjectId(projectId)).willThrow(
                new BusinessLogicException(ProjectErrorCode.PROJECT_NOT_FOUND)
            );

            BusinessLogicException exception =
                catchThrowableOfType(() -> projectLikeService.like(memberId, projectId),
                    BusinessLogicException.class);
            assertThat(exception.getErrorCode()).isEqualTo(ProjectErrorCode.PROJECT_NOT_FOUND);
        }
    }
}