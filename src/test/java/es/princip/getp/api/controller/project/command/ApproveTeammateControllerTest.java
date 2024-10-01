package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.project.apply.port.in.ApproveTeammateUseCase;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("피플은 팀원 신청을 승인할 수 있다.")
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
    void approveTeammate() throws Exception{
        doNothing().when(approveTeammateUseCase).approve(any(String.class));

        perform()
            .andExpect(status().isOk())
            .andDo(
                restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    queryParameters(parameterWithName("token").description("팀원 승인 토큰"))
                )
            )
            .andDo(print());
    }
}