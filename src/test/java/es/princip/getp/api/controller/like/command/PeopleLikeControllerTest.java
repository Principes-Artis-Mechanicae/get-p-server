package es.princip.getp.api.controller.like.command;

import es.princip.getp.api.controller.ControllerTest;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.application.like.exception.AlreadyLikedException;
import es.princip.getp.application.like.people.port.in.LikePeopleUseCase;
import es.princip.getp.application.like.people.port.in.UnlikePeopleUseCase;
import es.princip.getp.domain.member.model.MemberType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PeopleLikeControllerTest extends ControllerTest {

    @Autowired private LikePeopleUseCase likePeopleUseCase;
    @Autowired private UnlikePeopleUseCase unlikePeopleUseCase;

    @DisplayName("의뢰자는 피플에게 좋아요를 누를 수 있다.")
    @Nested
    class Like {

        private final Long peopleId = 1L;

        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @Test
        void like() throws Exception {
            willDoNothing().given(likePeopleUseCase).like(any(), eq(peopleId));

            mockMvc.perform(post("/people/{peopleId}/likes", peopleId))
                .andExpect(status().isCreated());
        }

        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @Test
        void like_WhenPeopleIsAlreadyLiked_ShouldBeFailed() throws Exception {
            willThrow(new AlreadyLikedException())
                .given(likePeopleUseCase).like(any(), eq(peopleId));

            mockMvc.perform(post("/people/{peopleId}/likes", peopleId))
                .andExpect(status().isConflict());
        }

        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        void like_WhenMemberTypeIsPeople_ShouldBeFailed() throws Exception {
            mockMvc.perform(post("/people/{peopleId}/likes", peopleId))
                .andExpect(status().isForbidden());
        }
    }

    @DisplayName("의뢰자는 피플에게 눌렀던 좋아요를 취소를 할 수 있다.")
    @Nested
    class Unlike {

        private final Long peopleId = 1L;

        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @Test
        void unlike() throws Exception {
            willDoNothing().given(unlikePeopleUseCase).unlike(any(), eq(peopleId));

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