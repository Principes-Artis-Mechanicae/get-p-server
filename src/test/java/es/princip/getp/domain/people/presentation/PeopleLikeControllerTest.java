package es.princip.getp.domain.people.presentation;

import es.princip.getp.domain.member.domain.MemberType;
import es.princip.getp.domain.people.exception.PeopleErrorCode;
import es.princip.getp.domain.people.exception.PeopleLikeErrorCode;
import es.princip.getp.domain.people.service.PeopleLikeService;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.exception.BusinessLogicException;
import es.princip.getp.infra.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static es.princip.getp.domain.member.domain.MemberType.ROLE_CLIENT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({PeopleLikeController.class, PeopleErrorCodeController.class})
public class PeopleLikeControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeopleLikeService peopleLikeService;

    @Nested
    class Like {

        private final Long peopleId = 1L;

        @DisplayName("의뢰자는 피플에게 좋아요를 누를 수 있다.")
        @WithCustomMockUser(memberType = ROLE_CLIENT)
        @Test
        void like() throws Exception {
            willDoNothing().given(peopleLikeService).like(any(), eq(peopleId));

            mockMvc.perform(post("/people/{peopleId}/likes", peopleId))
                .andExpect(status().isCreated());
        }

        @DisplayName("의뢰자는 이미 좋아요를 누른 피플에게 다시 좋아요를 누를 수 없다.")
        @WithCustomMockUser(memberType = ROLE_CLIENT)
        @Test
        void like_WhenPeopleIsAlreadyLiked_ShouldBeFailed() throws Exception {
            willThrow(new BusinessLogicException(PeopleLikeErrorCode.ALREADY_LIKED))
                .given(peopleLikeService).like(any(), eq(peopleId));

            mockMvc.perform(post("/people/{peopleId}/likes", peopleId))
                .andExpect(status().isConflict());
        }

        @DisplayName("피플은 피플에게 좋아요를 누를 수 없다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        void like_WhenMemberTypeIsPeople_ShouldBeFailed() throws Exception {
            mockMvc.perform(post("/people/{peopleId}/likes", peopleId))
                .andExpect(status().isForbidden());
        }

        @DisplayName("의뢰자는 존재하지 않는 피플에게 좋아요를 누를 수 없다.")
        @WithCustomMockUser(memberType = ROLE_CLIENT)
        @Test
        void like_WhenPeopleIsNotFound_ShouldBeFailed() throws Exception {
            willThrow(new BusinessLogicException(PeopleErrorCode.PEOPLE_NOT_FOUND))
                .given(peopleLikeService).like(any(), eq(peopleId));

            mockMvc.perform(post("/people/{peopleId}/likes", peopleId))
                .andExpect(status().isNotFound());
        }
    }
}