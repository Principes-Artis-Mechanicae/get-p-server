package es.princip.getp.domain.project.command.presentation;

import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.domain.project.command.application.ProjectMeetingService;
import es.princip.getp.domain.project.command.application.command.ScheduleMeetingCommand;
import es.princip.getp.domain.project.command.presentation.dto.request.ScheduleMeetingRequest;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static es.princip.getp.domain.member.fixture.PhoneNumberFixture.PHONE_NUMBER;
import static es.princip.getp.domain.project.fixture.ProjectMeetingFixture.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectMeetingController.class)
public class ProjectMeetingControllerTest extends AbstractControllerTest {

    @MockBean
    private ProjectCommandMapper projectCommandMapper;

    @MockBean
    private ProjectMeetingService projectMeetingService;
    
    @Nested
    @DisplayName("프로젝트 미팅 신청")
    class ScheduleMeeting{

        private final Long projectId = 1L;
        private final Long applicantId = 1L;
        private final Long memberId = 1L;

        private final ScheduleMeetingRequest request = new ScheduleMeetingRequest(
            applicantId,
            LOCATION,
            SCHEDULE,
            PHONE_NUMBER,
            DESCRIPTION
        );

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
