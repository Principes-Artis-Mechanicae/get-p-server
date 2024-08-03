package es.princip.getp.domain.project.command.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.domain.member.fixture.PhoneNumberFixture;
import es.princip.getp.domain.project.command.application.ProjectMeetingService;
import es.princip.getp.domain.project.command.application.command.ScheduleMeetingCommand;
import es.princip.getp.domain.project.command.presentation.dto.request.ScheduleMeetingRequest;
import es.princip.getp.domain.project.fixture.ProjectMeetingFixture;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.support.AbstractControllerTest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectMeetingController.class)
public class ProjectMeetingControllerTest extends AbstractControllerTest{

    @MockBean
    private ProjectCommandMapper projectCommandMapper;

    @MockBean
    ProjectMeetingService projectMeetingService;
    
    @Nested
    @DisplayName("의뢰자는")
    class ScheduleMeeting{
        private final ScheduleMeetingRequest request = new ScheduleMeetingRequest(
                ProjectMeetingFixture.applicantId,
                ProjectMeetingFixture.meetingLocation,
                ProjectMeetingFixture.meetingSchedules,
                PhoneNumberFixture.PHONE_NUMBER,
                ProjectMeetingFixture.description
            );
        
        private final Long projectId = 1L;
        private final Long memberId = 1L;
        
        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @DisplayName("프로젝트 지원자에게 미팅 신청을 할 수 있다.")
        void scheduleMeeting() throws Exception {
            given(projectCommandMapper.mapToCommand(memberId, projectId, request))
                .willReturn(mock(ScheduleMeetingCommand.class));

            mockMvc.perform(post("/projects/{projectId}/meetings", projectId)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());
        }
    }
}
