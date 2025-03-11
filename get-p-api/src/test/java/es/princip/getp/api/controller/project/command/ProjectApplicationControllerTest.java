package es.princip.getp.api.controller.project.command;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.controller.project.command.dto.request.ApplyProjectRequest;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.project.apply.dto.command.ApplyProjectCommand;
import es.princip.getp.application.project.apply.port.in.ApplyProjectUseCase;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.controller.project.command.description.ApplyProjectAsIndividualRequestDescription.applyProjectAsIndividualRequestDescription;
import static es.princip.getp.api.controller.project.command.description.ApplyProjectAsTeamRequestDescription.applyProjectAsTeamRequestDescription;
import static es.princip.getp.api.controller.project.command.description.ApplyProjectResponseDescription.applyProjectResponseDescription;
import static es.princip.getp.api.controller.project.command.fixture.ApplyProjectRequestFixture.applyProjectAsIndividualRequest;
import static es.princip.getp.api.controller.project.command.fixture.ApplyProjectRequestFixture.applyProjectAsTeamRequest;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectApplicationControllerTest extends ControllerTest {

    @Autowired private ApplyProjectUseCase applyProjectUseCase;

    @Nested
    class 프로젝트_지원 {

        private final ProjectId projectId = new ProjectId(1L);
        private final ProjectApplicationId applicationId = new ProjectApplicationId(1L);

        private ResultActions perform(final ApplyProjectRequest request) throws Exception {
            return mockMvc.perform(post("/projects/{projectId}/applications", projectId.getValue())
                    .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                    .content(objectMapper.writeValueAsString(request)));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        void 피플은_프로젝트에_개인으로_지원할_수_있다() throws Exception {
            final ApplyProjectRequest request = applyProjectAsIndividualRequest();
            given(applyProjectUseCase.apply(any(ApplyProjectCommand.class)))
                .willReturn(applicationId);

            perform(request)
                .andExpect(status().isCreated())
                .andDo(document("project/apply-project-as-individual",
                    ResourceSnippetParameters.builder()
                        .tag("프로젝트 지원")
                        .description("피플은 프로젝트에 개인/팀으로 지원할 수 있다.")
                        .summary("프로젝트 지원")
                        .requestSchema(Schema.schema("ApplyProjectAsIndividualRequest"))
                        .responseSchema(Schema.schema("ApplyProjectResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    pathParameters(parameterWithName("projectId").description("프로젝트 ID")),
                    requestFields(applyProjectAsIndividualRequestDescription()),
                    responseFields(applyProjectResponseDescription())
                ))
                .andDo(print());
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        void 피플은_프로젝트에_팀으로_지원할_수_있다() throws Exception {
            final ApplyProjectRequest request = applyProjectAsTeamRequest();
            given(applyProjectUseCase.apply(any(ApplyProjectCommand.class)))
                .willReturn(applicationId);

            perform(request)
                .andExpect(status().isCreated())
                .andDo(document("project/apply-project-as-team",
                    ResourceSnippetParameters.builder()
                        .tag("프로젝트 지원")
                        .description("피플은 프로젝트에 개인/팀으로 지원할 수 있다.")
                        .summary("프로젝트 지원")
                        .requestSchema(Schema.schema("ApplyProjectAsTeamRequest"))
                        .responseSchema(Schema.schema("ApplyProjectResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    pathParameters(parameterWithName("projectId").description("프로젝트 ID")),
                    requestFields(applyProjectAsTeamRequestDescription()),
                    responseFields(applyProjectResponseDescription())
                ))
                .andDo(print());
        }
    }
}