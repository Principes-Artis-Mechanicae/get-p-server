package es.princip.getp.api.controller.project.command;

import es.princip.getp.api.controller.ControllerTest;
import es.princip.getp.api.controller.project.command.description.ScheduleMeetingRequestDescription;
import es.princip.getp.api.controller.project.command.description.ScheduleMeetingResponseDescription;
import es.princip.getp.api.controller.project.command.dto.request.ScheduleMeetingRequest;
import es.princip.getp.api.docs.PayloadDocumentationHelper;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.domain.project.command.application.ProjectMeetingService;
import es.princip.getp.domain.project.command.application.command.ScheduleMeetingCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.ResultActions;

import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.domain.member.fixture.PhoneNumberFixture.PHONE_NUMBER;
import static es.princip.getp.domain.project.fixture.ProjectMeetingFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectMeetingControllerTest extends ControllerTest {

    @Autowired
    private ProjectCommandMapper projectCommandMapper;

    @Autowired
    private ProjectMeetingService projectMeetingService;
    
    @Nested
    @DisplayName("프로젝트 미팅 신청")
    class ScheduleMeeting {

        private final Long projectId = 1L;
        private final Long memberId = 1L;
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
            return mockMvc.perform(post("/projects/{projectId}/meetings", projectId)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .content(objectMapper.writeValueAsString(request)));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @DisplayName("의뢰자는 프로젝트 지원자에게 미팅 신청을 할 수 있다.")
        void scheduleMeeting() throws Exception {
            given(projectCommandMapper.mapToCommand(memberId, projectId, request))
                .willReturn(mock(ScheduleMeetingCommand.class));
            given(projectMeetingService.ScheduleMeeting(any(ScheduleMeetingCommand.class)))
                .willReturn(meetingId);

            perform()
                .andExpect(status().isCreated())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        PayloadDocumentation.requestFields(ScheduleMeetingRequestDescription.description()),
                        PayloadDocumentationHelper.responseFields(ScheduleMeetingResponseDescription.description())
                    )
                )
                .andDo(print());
        }
    }
}
