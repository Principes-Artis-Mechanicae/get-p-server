package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.project.command.description.ApplyProjectAsIndividualRequestDescription;
import es.princip.getp.api.controller.project.command.description.ApplyProjectAsTeamRequestDescription;
import es.princip.getp.api.controller.project.command.description.ApplyProjectResponseDescription;
import es.princip.getp.api.controller.project.command.dto.request.ApplyProjectRequest;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.project.apply.command.ApplyProjectCommand;
import es.princip.getp.application.project.apply.port.in.ApplyProjectUseCase;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static es.princip.getp.api.controller.project.command.ApplyProjectRequestFixture.applyProjectAsIndividualRequest;
import static es.princip.getp.api.controller.project.command.ApplyProjectRequestFixture.applyProjectAsTeamRequest;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.api.docs.PayloadDocumentationHelper.responseFields;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectApplicationControllerTest extends ControllerTest {

    @Autowired private ProjectCommandMapper projectCommandMapper;
    @Autowired private ApplyProjectUseCase applyProjectUseCase;

    @BeforeEach
    void setUp() {
        given(projectCommandMapper.mapToCommand(
            any(MemberId.class),
            any(ProjectId.class),
            any(ApplyProjectRequest.class)
        ))
        .willReturn(mock(ApplyProjectCommand.class));
    }

    @Nested
    @DisplayName("프로젝트 지원")
    class ApplyForProject {

        private final ProjectId projectId = new ProjectId(1L);
        private final ProjectApplicationId applicationId = new ProjectApplicationId(1L);

        private ResultActions perform(final ApplyProjectRequest request) throws Exception {
            return mockMvc.perform(post("/projects/{projectId}/applications", projectId.getValue())
                    .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                    .content(objectMapper.writeValueAsString(request)));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @DisplayName("피플은 프로젝트에 개인으로 지원할 수 있다.")
        void applyProjectAsIndividual() throws Exception {
            final ApplyProjectRequest request = applyProjectAsIndividualRequest();
            given(applyProjectUseCase.apply(any(ApplyProjectCommand.class)))
                .willReturn(applicationId);

            perform(request)
                .andExpect(status().isCreated())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        pathParameters(parameterWithName("projectId").description("프로젝트 ID")),
                        requestFields(ApplyProjectAsIndividualRequestDescription.description()),
                        responseFields(ApplyProjectResponseDescription.description())
                    )
                )
                .andDo(print());
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @DisplayName("피플은 프로젝트에 팀으로 지원할 수 있다.")
        void applyProjectAsTeam() throws Exception {
            final ApplyProjectRequest request = applyProjectAsTeamRequest();
            given(applyProjectUseCase.apply(any(ApplyProjectCommand.class)))
                .willReturn(applicationId);

            perform(request)
                .andExpect(status().isCreated())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        pathParameters(parameterWithName("projectId").description("프로젝트 ID")),
                        requestFields(ApplyProjectAsTeamRequestDescription.description()),
                        responseFields(ApplyProjectResponseDescription.description())
                    )
                )
                .andDo(print());
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @DisplayName("의뢰자는 프로젝트에 지원할 수 없다.")
        void applyProject_WhenMemberTypeIsClient_ShouldFail() throws Exception {
            final ApplyProjectRequest request = applyProjectAsIndividualRequest();
            perform(request)
                .andExpect(status().isForbidden());
        }
    }
}