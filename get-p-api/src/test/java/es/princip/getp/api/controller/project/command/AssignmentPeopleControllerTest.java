package es.princip.getp.api.controller.project.command;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.controller.project.command.dto.request.AssignmentPeopleRequest;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.project.assign.dto.command.AssignmentPeopleCommand;
import es.princip.getp.application.project.assign.port.in.AssignmentPeopleUseCase;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.domain.project.confirmation.model.AssignmentPeopleId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.controller.project.command.description.ConfirmProjectRequestDescription.confirmProjectRequestDescription;
import static es.princip.getp.api.controller.project.command.description.ConfirmProjectResponseDescription.confirmProjectResponseDescription;
import static es.princip.getp.api.controller.project.command.fixture.AssignmentPeopleRequestFixture.assignmentPeopleRequest;
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

class AssignmentPeopleControllerTest extends ControllerTest {

    @Autowired private AssignmentPeopleUseCase assignmentPeopleUseCase;

    @Nested
    class 프로젝트_확정 {
        private final ProjectId projectId = new ProjectId(1L);
        private final AssignmentPeopleId assignmentPeopleId = new AssignmentPeopleId(1L);

        private ResultActions perform(final AssignmentPeopleRequest request) throws Exception {
            return mockMvc.perform(post("/projects/{projectId}/assignment", projectId.getValue())
                    .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                    .content(objectMapper.writeValueAsString(request)));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void 의뢰자는_지원자를_확정할_수_있다() throws Exception {
            final AssignmentPeopleRequest request = assignmentPeopleRequest();
            given(assignmentPeopleUseCase.assign(any(AssignmentPeopleCommand.class)))
                    .willReturn(assignmentPeopleId);

            perform(request)
                .andExpect(status().isCreated())
                .andDo(document("project/assignment",
                    ResourceSnippetParameters.builder()
                        .tag("프로젝트 지원자 확정")
                        .description("의뢰자는 지원자를 확정할 수 있다.")
                        .summary("프로젝트 확정")
                        .requestSchema(Schema.schema("AssignmentPeopleRequest"))
                        .responseSchema(Schema.schema("AssignmentPeopleResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    pathParameters(parameterWithName("projectId").description("프로젝트 ID")),
                    requestFields(confirmProjectRequestDescription()),
                    responseFields(confirmProjectResponseDescription())
                ))
                .andDo(print());
        }
    }
}