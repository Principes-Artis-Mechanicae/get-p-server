package es.princip.getp.domain.like.command.presentation;

import es.princip.getp.domain.like.command.application.PeopleLikeService;
import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.domain.people.exception.NotFoundPeopleException;
import es.princip.getp.domain.project.exception.AlreadyLikedProjectException;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PeopleLikeController.class)
public class PeopleLikeControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeopleLikeService peopleLikeService;

    @DisplayName("의뢰자는 피플에게 좋아요를 누를 수 있다.")
    @Nested
    class Like {

        private final Long peopleId = 1L;

        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @Test
        void like() throws Exception {
            willDoNothing().given(peopleLikeService).like(any(), eq(peopleId));

            mockMvc.perform(post("/people/{peopleId}/likes", peopleId))
                .andExpect(status().isCreated());
        }

        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @Test
        void like_WhenPeopleIsAlreadyLiked_ShouldBeFailed() throws Exception {
            willThrow(new AlreadyLikedProjectException())
                .given(peopleLikeService).like(any(), eq(peopleId));

            mockMvc.perform(post("/people/{peopleId}/likes", peopleId))
                .andExpect(status().isConflict());
        }

        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        void like_WhenMemberTypeIsPeople_ShouldBeFailed() throws Exception {
            mockMvc.perform(post("/people/{peopleId}/likes", peopleId))
                .andExpect(status().isForbidden());
        }

        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @Test
        void like_WhenPeopleIsNotFound_ShouldBeFailed() throws Exception {
            willThrow(new NotFoundPeopleException())
                .given(peopleLikeService).like(any(), eq(peopleId));

            mockMvc.perform(post("/people/{peopleId}/likes", peopleId))
                .andExpect(status().isNotFound());
        }
    }

    @DisplayName("의뢰자는 피플에게 눌렀던 좋아요를 취소를 할 수 있다.")
    @Nested
    class Unlike {

        private final Long peopleId = 1L;

        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @Test
        void unlike() throws Exception {
            willDoNothing().given(peopleLikeService).unlike(any(), eq(peopleId));

            mockMvc.perform(delete("/people/{peopleId}/likes", peopleId))
                .andExpect(status().isNoContent());
        }

        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        void unlike_WhenMemberTypeIsPeople_ShouldBeFailed() throws Exception {
            mockMvc.perform(delete("/people/{peopleId}/likes", peopleId))
                .andExpect(status().isForbidden());
        }
    }
}