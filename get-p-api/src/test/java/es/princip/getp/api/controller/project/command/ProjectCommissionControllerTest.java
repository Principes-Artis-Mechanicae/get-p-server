package es.princip.getp.api.controller.project.command;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.controller.project.command.dto.request.CommissionProjectRequest;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.project.commission.ProjectCommissionService;
import es.princip.getp.application.project.commission.dto.command.CommissionProjectCommand;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.controller.project.command.description.CommissionProjectRequestDescription.commissionProjectRequestDescription;
import static es.princip.getp.api.controller.project.command.description.CommissionProjectResponseDescription.commissionProjectResponseDescription;
import static es.princip.getp.api.controller.project.command.fixture.CommissionProjectRequestFixture.commissionProjectRequest;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectCommissionControllerTest extends ControllerTest {

    @Autowired private ProjectCommissionService projectCommissionService;

    @Nested
    class 프로젝트_의뢰 {

        private final CommissionProjectRequest request = commissionProjectRequest();

        private ResultActions perform() throws Exception {
            return mockMvc.perform(post("/projects")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .content(objectMapper.writeValueAsString(request)));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void 의뢰자는_프로젝트를_의뢰할_수_있다() throws Exception {
            given(projectCommissionService.commission(any(CommissionProjectCommand.class)))
                .willReturn(new ProjectId(1L));

            perform()
                .andExpect(status().isCreated())
                .andDo(document("project/commission-project",
                    ResourceSnippetParameters.builder()
                        .tag("프로젝트 의뢰")
                        .description("의뢰자는 프로젝트를 의뢰할 수 있다.")
                        .summary("프로젝트 의뢰")
                        .requestSchema(Schema.schema("CommissionProjectRequest"))
                        .responseSchema(Schema.schema("CommissionProjectResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    requestFields(commissionProjectRequestDescription()),
                    responseFields(commissionProjectResponseDescription())
                ))
                .andDo(print());
        }
    }
}