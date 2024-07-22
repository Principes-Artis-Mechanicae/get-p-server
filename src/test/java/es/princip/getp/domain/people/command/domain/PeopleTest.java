package es.princip.getp.domain.people.command.domain;

import es.princip.getp.domain.people.exception.ProjectAlreadyLikedException;
import es.princip.getp.domain.people.exception.ProjectNeverLikedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static es.princip.getp.domain.member.fixture.EmailFixture.email;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("피플")
class PeopleTest {

    @Nested
    class 프로젝트_좋아요 {

        @Test
        void 피플은_프로젝트에_좋아요를_누를_수_있다() {
            final People people = People.builder()
                .email(email())
                .peopleType(PeopleType.INDIVIDUAL)
                .memberId(1L)
                .build();
            final Long projectId = 1L;

            people.likeProject(projectId);

            assertThat(people.getLikedProjects()).contains(projectId);
        }

        @Test
        void 피플은_프로젝트에_좋아요를_중복으로_누를_수_없다() {
            final People people = People.builder()
                .email(email())
                .peopleType(PeopleType.INDIVIDUAL)
                .memberId(1L)
                .build();
            final Long projectId = 1L;
            people.likeProject(projectId);

            assertThatThrownBy(() -> people.likeProject(projectId))
                .isInstanceOf(ProjectAlreadyLikedException.class);
        }
    }

    @Nested
    class 프로젝트_좋아요_취소 {

        @Test
        void 피플은_프로젝트에_눌렀던_좋아요를_취소할_수_있다() {
            final People people = People.builder()
                .email(email())
                .peopleType(PeopleType.INDIVIDUAL)
                .memberId(1L)
                .build();
            final Long projectId = 1L;
            people.likeProject(projectId);

            people.unlikeProject(projectId);

            assertThat(people.getLikedProjects()).doesNotContain(projectId);
        }

        @Test
        void 피플은_좋아요를_누른적이_없는_프로젝트에_좋아요를_취소할_수_없다() {
            final People people = People.builder()
                .email(email())
                .peopleType(PeopleType.INDIVIDUAL)
                .memberId(1L)
                .build();
            final Long projectId = 1L;

            assertThatThrownBy(() -> people.unlikeProject(projectId))
                .isInstanceOf(ProjectNeverLikedException.class);
        }
    }
}