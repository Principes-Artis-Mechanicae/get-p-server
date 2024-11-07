package es.princip.getp.api.controller.project.command;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.project.apply.port.in.ApproveTeammateUseCase;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static es.princip.getp.api.docs.StatusFieldDescriptor.statusField;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApproveTeammateControllerTest extends ControllerTest {

    @Autowired private ApproveTeammateUseCase approveTeammateUseCase;

    private final ProjectApplicationId applicationId = new ProjectApplicationId(1L);

    private ResultActions perform() throws Exception {
        return mockMvc.perform(get("/teammates/approve", applicationId.getValue())
            .header("Authorization", "Bearer ${ACCESS_TOKEN}")
            .queryParam("token", "${TEAMMATE_APPROVAL_TOKEN}"));
    }

    @Test
    @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
    void 피플은_팀원_신청을_승인할_수_있다() throws Exception{
        doNothing().when(approveTeammateUseCase).approve(any(String.class));

        perform()
            .andExpect(status().isOk())
            .andDo(document("project/approve-teammate",
                ResourceSnippetParameters.builder()
                    .tag("프로젝트 지원")
                    .description("피플은 팀원 신청을 승인할 수 있다.")
                    .summary("프로젝트 팀원 신청 승인")
                    .responseSchema(Schema.schema("StatusResponse")),
                requestHeaders(authorizationHeaderDescription()),
                queryParameters(parameterWithName("token").description("팀원 승인 토큰")),
                responseFields(statusField())
            ))
            .andDo(print());
    }
}