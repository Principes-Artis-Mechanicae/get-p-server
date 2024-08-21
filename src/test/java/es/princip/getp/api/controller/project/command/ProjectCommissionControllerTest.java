package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.ControllerTest;
import es.princip.getp.api.controller.project.command.description.RegisterProjectRequestDescription;
import es.princip.getp.api.controller.project.command.description.RegisterProjectResponseDescription;
import es.princip.getp.api.controller.project.command.dto.request.CommissionProjectRequest;
import es.princip.getp.api.docs.PayloadDocumentationHelper;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.project.command.application.ProjectCommissionService;
import es.princip.getp.domain.project.command.application.command.RegisterProjectCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.ResultActions;

import static es.princip.getp.api.controller.project.command.CommissionProjectRequestFixture.registerProjectRequest;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectCommissionControllerTest extends ControllerTest {

    @Autowired
    private ProjectCommissionService projectCommissionService;

    @Autowired
    private ProjectCommandMapper projectCommandMapper;

    @DisplayName("프로젝트 의뢰")
    @Nested
    class CommissionProject {

        final CommissionProjectRequest request = registerProjectRequest();

        private ResultActions perform() throws Exception {
            return mockMvc.perform(post("/projects")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .content(objectMapper.writeValueAsString(request)));
        }

        @Test
        @DisplayName("의뢰자는 프로젝트를 의뢰할 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void commissionProject() throws Exception {
            given(projectCommandMapper.mapToCommand(anyLong(), any(CommissionProjectRequest.class)))
                .willReturn(mock(RegisterProjectCommand.class));
            given(projectCommissionService.commissionProject(any(RegisterProjectCommand.class)))
                .willReturn(1L);

            perform()
                .andExpect(status().isCreated())
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    PayloadDocumentation.requestFields(RegisterProjectRequestDescription.description()),
                    PayloadDocumentationHelper.responseFields(RegisterProjectResponseDescription.description())
                ))
                .andDo(print());
        }
    }
}