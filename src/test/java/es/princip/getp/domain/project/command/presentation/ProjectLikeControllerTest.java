package es.princip.getp.domain.project.command.presentation;

import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.domain.project.command.application.ProjectLikeService;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectLikeController.class)
public class ProjectLikeControllerTest extends AbstractControllerTest {

    @MockBean
    private ProjectLikeService projectLikeService;

    @Nested
    @DisplayName("프로젝트 좋아요")
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

        @DisplayName("의뢰자는 프로젝트에 좋아요를 누를 수 없다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @Test
        void like_WhenMemberTypeIsClient_ShouldBeFailed() throws Exception {
            mockMvc.perform(post("/projects/{projectId}/likes", projectId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
        }
    }
}
