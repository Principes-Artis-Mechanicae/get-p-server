package es.princip.getp.domain.people.controller;

import es.princip.getp.domain.member.domain.enums.MemberType;
import es.princip.getp.domain.people.exception.PeopleErrorCode;
import es.princip.getp.domain.people.exception.PeopleLikeErrorCode;
import es.princip.getp.domain.people.service.PeopleLikeService;
import es.princip.getp.global.config.SecurityConfig;
import es.princip.getp.global.config.SecurityTestConfig;
import es.princip.getp.global.exception.BusinessLogicException;
import es.princip.getp.global.mock.PrincipalDetailsParameterResolver;
import es.princip.getp.global.mock.WithCustomMockUser;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@WebMvcTest(PeopleLikeController.class)
@Import({SecurityConfig.class, SecurityTestConfig.class})
@ActiveProfiles("test")
@ExtendWith(PrincipalDetailsParameterResolver.class)
public class PeopleLikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeopleLikeService peopleLikeService;

    @Nested
    class Like {

        private final Long peopleId = 1L;

        @DisplayName("의뢰자는 피플에게 좋아요를 누를 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @Test
        void like() throws Exception {
            willDoNothing().given(peopleLikeService).like(any(), eq(peopleId));

            mockMvc.perform(post("/people/{peopleId}/likes", peopleId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        }

        @DisplayName("의뢰자는 피플에게 중복으로 좋아요를 누를 수 없다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @Test
        void like_WhenPeopleIsAlreadyLiked_ShouldBeFailed() throws Exception {
            willThrow(new BusinessLogicException(PeopleLikeErrorCode.ALREADY_LIKED))
                .given(peopleLikeService).like(any(), eq(peopleId));

            mockMvc.perform(post("/people/{peopleId}/likes", peopleId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
        }

        @DisplayName("피플은 피플에게 좋아요를 누를 수 없다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        void like_WhenMemberTypeIsPeople_ShouldBeFailed() throws Exception {
            mockMvc.perform(post("/people/{peopleId}/likes", peopleId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
        }

        @DisplayName("의뢰자는 존재하지 않는 피플에게 좋아요를 누를 수 없다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @Test
        void like_WhenPeopleIsNotFound_ShouldBeFailed() throws Exception {
            willThrow(new BusinessLogicException(PeopleErrorCode.PEOPLE_NOT_FOUND))
                .given(peopleLikeService).like(any(), eq(peopleId));

            mockMvc.perform(post("/people/{peopleId}/likes", peopleId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        }
    }

    @Nested
    class Unlike {

        private final Long peopleId = 1L;

        @DisplayName("의뢰자는 피플에게 눌렀던 좋아요를 취소를 할 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @Test
        void unlike() throws Exception {
            willDoNothing().given(peopleLikeService).unlike(any(), eq(peopleId));

            mockMvc.perform(delete("/people/{peopleId}/likes", peopleId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        }

        @DisplayName("피플은 피플에게 좋아요를 취소할 수 없다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        void unlike_WhenMemberTypeIsPeople_ShouldBeFailed() throws Exception {
            mockMvc.perform(delete("/people/{peopleId}/likes", peopleId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
        }
    }
}