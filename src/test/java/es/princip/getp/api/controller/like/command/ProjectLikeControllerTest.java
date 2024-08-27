package es.princip.getp.api.controller.like.command;

import es.princip.getp.api.controller.ControllerTest;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.domain.like.command.application.ProjectLikeService;
import es.princip.getp.domain.member.model.MemberType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectLikeControllerTest extends ControllerTest {

    @Autowired
    private ProjectLikeService projectLikeService;

    @Nested
    @DisplayName("프로젝트 좋아요")
    class LikeProject {

        private final Long projectId = 1L;

        private ResultActions perform() throws Exception {
            return mockMvc.perform(post("/projects/{projectId}/likes", projectId)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @DisplayName("피플은 프로젝트에 좋아요를 누를 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        void likeProject() throws Exception {
            willDoNothing().given(projectLikeService).like(anyLong(), anyLong());

            perform()
                .andExpect(status().isCreated())
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor())
                ))
                .andDo(print());
        }
    }

    @Nested
    @DisplayName("프로젝트 좋아요 취소")
    class UnlikeProject {

        private final Long projectId = 1L;

        private ResultActions perform() throws Exception {
            return mockMvc.perform(delete("/projects/{projectId}/likes", projectId)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @DisplayName("피플은 프로젝트에 눌렀던 좋아요를 취소할 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @Test
        void unlikeProject() throws Exception {
            willDoNothing().given(projectLikeService).unlike(anyLong(), anyLong());

            perform()
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor())
                ))
                .andDo(print());
        }
    }
}
