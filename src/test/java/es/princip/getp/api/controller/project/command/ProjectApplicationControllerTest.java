package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.ControllerTest;
import es.princip.getp.api.controller.project.command.description.ApplyProjectRequestDescription;
import es.princip.getp.api.controller.project.command.description.ApplyProjectResponseDescription;
import es.princip.getp.api.controller.project.command.dto.request.ApplyProjectRequest;
import es.princip.getp.api.docs.PayloadDocumentationHelper;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.application.project.apply.command.ApplyProjectCommand;
import es.princip.getp.application.project.apply.port.in.ApplyProjectUseCase;
import es.princip.getp.domain.member.model.MemberType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.PayloadDocumentation;

import static es.princip.getp.api.controller.project.command.ApplyProjectRequestFixture.applyProjectRequest;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectApplicationControllerTest extends ControllerTest {

    @Autowired
    private ProjectCommandMapper projectCommandMapper;

    @Autowired
    private ApplyProjectUseCase applyProjectUseCase;

    @BeforeEach
    void setUp() {
        given(projectCommandMapper.mapToCommand(anyLong(), anyLong(), any(ApplyProjectRequest.class)))
            .willReturn(mock(ApplyProjectCommand.class));
    }

    @Nested
    @DisplayName("프로젝트 지원")
    class ApplyForProject {

        private final Long projectId = 1L;
        private final Long applicationId = 1L;
        private final ApplyProjectRequest request = applyProjectRequest();

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @DisplayName("피플은 프로젝트에 지원할 수 있다.")
        void applyForProject() throws Exception {
            given(applyProjectUseCase.apply(any(ApplyProjectCommand.class)))
                .willReturn(applicationId);

            mockMvc.perform(post("/projects/{projectId}/applications", projectId)
                    .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        pathParameters(parameterWithName("projectId").description("프로젝트 ID")),
                        PayloadDocumentation.requestFields(ApplyProjectRequestDescription.description()),
                        PayloadDocumentationHelper.responseFields(ApplyProjectResponseDescription.description())
                    )
                )
                .andDo(print());
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @DisplayName("의뢰자는 프로젝트에 지원할 수 없다.")
        void applyForProject_WhenMemberTypeIsClient_ShouldFail() throws Exception {
            mockMvc.perform(post("/projects/{projectId}/applications", projectId)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
        }
    }
}