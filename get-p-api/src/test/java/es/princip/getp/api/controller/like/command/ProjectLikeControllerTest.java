package es.princip.getp.api.controller.like.command;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.like.project.port.in.LikeProjectUseCase;
import es.princip.getp.application.like.project.port.in.UnlikeProjectUseCase;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectLikeControllerTest extends ControllerTest {

    @Autowired private LikeProjectUseCase likeProjectUseCase;
    @Autowired private UnlikeProjectUseCase unlikeProjectUseCase;

    @Nested
    class 프로젝트_좋아요 {

        private final MemberId memberId = new MemberId(1L);
        private final ProjectId projectId = new ProjectId(1L);

        private ResultActions perform() throws Exception {
            return mockMvc.perform(post("/projects/{projectId}/likes", projectId.getValue())
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        void 피플은_프로젝트에_좋아요를_누를_수_있다() throws Exception {
            willDoNothing().given(likeProjectUseCase).like(memberId, projectId);

            perform()
                .andExpect(status().isCreated())
                .andDo(document("like/like-project",
                    ResourceSnippetParameters.builder()
                        .tag("좋아요")
                        .description("피플은 프로젝트에 좋아요를 누를 수 있다.")
                        .summary("프로젝트 좋아요")
                        .responseSchema(Schema.schema("StatusResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    responseFields(statusField())
                ))
                .andDo(print());
        }
    }

    @Nested
    class 프로젝트_좋아요_취소 {

        private final MemberId memberId = new MemberId(1L);
        private final ProjectId projectId = new ProjectId(1L);

        private ResultActions perform() throws Exception {
            return mockMvc.perform(delete("/projects/{projectId}/likes", projectId.getValue())
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        void 피플은_프로젝트에_눌렀던_좋아요를_취소할_수_있다() throws Exception {
            willDoNothing().given(unlikeProjectUseCase).unlike(memberId, projectId);

            perform()
                .andExpect(status().isNoContent())
                .andDo(document("like/unlike-project",
                    ResourceSnippetParameters.builder()
                        .tag("좋아요")
                        .description("피플은 프로젝트에 눌렀던 좋아요를 취소할 수 있다.")
                        .summary("프로젝트 좋아요 취소")
                        .responseSchema(Schema.schema("StatusResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    responseFields(statusField())
                ))
                .andDo(print());
        }

    }
}
