package es.princip.getp.domain.project.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import es.princip.getp.domain.member.domain.enums.MemberType;
import es.princip.getp.domain.project.exception.ProjectErrorCode;
import es.princip.getp.domain.project.exception.ProjectLikeErrorCode;
import es.princip.getp.domain.project.service.ProjectLikeService;
import es.princip.getp.global.config.SecurityConfig;
import es.princip.getp.global.config.SecurityTestConfig;
import es.princip.getp.global.exception.BusinessLogicException;
import es.princip.getp.global.mock.PrincipalDetailsParameterResolver;
import es.princip.getp.global.mock.WithCustomMockUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@WebMvcTest(ProjectLikeController.class)
@Import({SecurityConfig.class, SecurityTestConfig.class})
@ActiveProfiles("test")
@ExtendWith(PrincipalDetailsParameterResolver.class)
@RequiredArgsConstructor
@Slf4j
public class ProjectLikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectLikeService projectLikeService;

    @Nested
    class Like {

        private final Long projectId = 1L;

        @DisplayName("피플은 프로젝트에 좋아요를 누를 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        void like() throws Exception {
            willDoNothing().given(projectLikeService).like(any(), any());

            mockMvc.perform(post("/projects/{projectId}/likes", projectId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        }

        @DisplayName("이미 좋아요를 누른 프로젝트에 다시 좋아요를 누를 수 없다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        void like_WhenProjectIsAlreadyLiked_ShouldBeFailed() throws Exception {
            willThrow(new BusinessLogicException(ProjectLikeErrorCode.ALREADY_LIKED))
                .given(projectLikeService).like(any(), any());

            mockMvc.perform(post("/projects/{projectId}/likes", projectId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
        }

        @DisplayName("의뢰자는 프로젝트에 좋아요를 누를 수 없다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @Test
        void like_WhenMemberTypeIsClient_ShouldBeFailed() throws Exception {
            mockMvc.perform(post("/projects/{projectId}/likes", projectId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
        }

        @DisplayName("존재하지 않는 프로젝트에 좋아요를 누를 수 없다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        void like_WhenProjectIsNotFound_ShouldBeFailed() throws Exception {
            willThrow(new BusinessLogicException(ProjectErrorCode.PROJECT_NOT_FOUND))
                .given(projectLikeService).like(any(), any());

            mockMvc.perform(post("/projects/{projectId}/likes", projectId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        }
    }
}
