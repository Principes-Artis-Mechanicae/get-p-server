package es.princip.getp.domain.project.command.presentation;

import es.princip.getp.common.util.ControllerTest;
import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.domain.project.command.application.ProjectCommissionService;
import es.princip.getp.domain.project.command.application.command.RegisterProjectCommand;
import es.princip.getp.domain.project.command.presentation.description.RegisterProjectRequestDescription;
import es.princip.getp.domain.project.command.presentation.description.RegisterProjectResponseDescription;
import es.princip.getp.domain.project.command.presentation.dto.request.CommissionProjectRequest;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static es.princip.getp.domain.project.fixture.CommissionProjectRequestFixture.registerProjectRequest;
import static es.princip.getp.infra.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.infra.docs.PayloadDocumentationHelper.responseFields;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
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
                    requestFields(RegisterProjectRequestDescription.description()),
                    responseFields(RegisterProjectResponseDescription.description())
                ))
                .andDo(print());
        }
    }
}