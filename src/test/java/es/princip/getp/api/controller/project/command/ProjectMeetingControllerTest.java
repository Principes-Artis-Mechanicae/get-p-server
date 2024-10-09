package es.princip.getp.api.controller.project.command;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.controller.project.command.dto.request.ScheduleMeetingRequest;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.project.meeting.ProjectMeetingService;
import es.princip.getp.application.project.meeting.command.ScheduleMeetingCommand;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.controller.project.command.description.ScheduleMeetingRequestDescription.scheduleMeetingRequestDescription;
import static es.princip.getp.api.controller.project.command.description.ScheduleMeetingResponseDescription.scheduleMeetingResponseDescription;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static es.princip.getp.fixture.member.PhoneNumberFixture.PHONE_NUMBER;
import static es.princip.getp.fixture.project.ProjectMeetingFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectMeetingControllerTest extends ControllerTest {

    @Autowired private ProjectMeetingService projectMeetingService;
    
    @Nested
    class 프로젝트_미팅_신청 {

        private final ProjectId projectId = new ProjectId(1L);
        private final Long meetingId = 1L;
        private final Long applicantId = 1L;

        private final ScheduleMeetingRequest request = new ScheduleMeetingRequest(
            applicantId,
            LOCATION,
            SCHEDULE,
            PHONE_NUMBER,
            DESCRIPTION
        );

        private ResultActions perform() throws Exception {
            return mockMvc.perform(post("/projects/{projectId}/meetings", projectId.getValue())
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .content(objectMapper.writeValueAsString(request)));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        void 의뢰자는_프로젝트_지원자에게_미팅_신청을_할_수_있다() throws Exception {
            given(projectMeetingService.scheduleMeeting(any(ScheduleMeetingCommand.class)))
                .willReturn(meetingId);

            perform()
                .andExpect(status().isCreated())
                .andDo(document("project/schedule-meeting",
                    ResourceSnippetParameters.builder()
                        .tag("프로젝트 관리")
                        .description("의뢰자는 프로젝트 지원자에게 미팅 신청을 할 수 있다.")
                        .summary("프로젝트 미팅 신청")
                        .requestSchema(Schema.schema("ScheduleMeetingRequest"))
                        .responseSchema(Schema.schema("ScheduleMeetingResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    requestFields(scheduleMeetingRequestDescription()),
                    responseFields(scheduleMeetingResponseDescription())
                ))
                .andDo(print());
        }
    }
}
