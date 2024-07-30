package es.princip.getp.domain.project.command.presentation;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.domain.project.command.application.ProjectMeetingApplyService;
import es.princip.getp.domain.project.command.presentation.dto.MeetingSchedule;
import es.princip.getp.domain.project.command.presentation.dto.request.ApplyProjectMeetingRequest;

import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.support.AbstractControllerTest;

@WebMvcTest(ProjectMeetingApplyController.class)
public class ProjectMeetingApplyControllerTest extends AbstractControllerTest{
    
    @MockBean
    private ProjectMeetingApplyService projectMeetingApplyService;

    @Nested
    @DisplayName("프로젝트 미팅 신청")
    class applyForProjectMeeting {

        private final List<MeetingSchedule> meetingSchedules = List.of(
            MeetingSchedule.of(LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(1L)),
            MeetingSchedule.of(LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(2L)),
            MeetingSchedule.of(LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(3L))
        );
        
        private final Long applicationId = 1L;
        private final ApplyProjectMeetingRequest request = new ApplyProjectMeetingRequest(
            1L,
            "AI챗봇 개발",
            "디스코드",
            meetingSchedules,
            "010-1234-5678",
            "AI 챗봇 개발 백엔드 분야로 미팅 요청 드립니다."
        );

        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @DisplayName("의뢰자는 미팅을 신청할 수 있다.")
        @Test
        void 프로젝트_미팅_신청하기() throws Exception{            
            mockMvc.perform(post("/projects/meeting/{applicationId}/apply", applicationId)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        }
    }
}
