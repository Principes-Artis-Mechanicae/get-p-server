package es.princip.getp.domain.project.presentation;

import es.princip.getp.domain.common.domain.Duration;
import es.princip.getp.domain.member.domain.model.MemberType;
import es.princip.getp.domain.project.command.application.ProjectApplicationService;
import es.princip.getp.domain.project.command.presentation.ProjectApplicationController;
import es.princip.getp.domain.project.command.presentation.dto.request.ApplyProjectRequest;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectApplicationController.class)
class ProjectApplicationControllerTest extends AbstractControllerTest {

    @MockBean
    private ProjectApplicationService projectApplicationService;

    @Nested
    @DisplayName("프로젝트 지원")
    class ApplyForProject {

        private final Long memberId = 1L;
        private final Long projectId = 1L;
        private final Long applicationId = 1L;
        private final ApplyProjectRequest request = new ApplyProjectRequest(
            Duration.of(LocalDate.now(), LocalDate.now()),
            "설명",
            List.of()
        );

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @DisplayName("피플은 프로젝트에 지원할 수 있다.")
        void applyForProject() throws Exception {
            given(projectApplicationService.applyForProject(memberId, projectId, request))
                .willReturn(applicationId);

            mockMvc.perform(post("/projects/{projectId}/applications", projectId)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @DisplayName("의뢰자는 프로젝트에 지원할 수 없다.")
        void applyForProject_WhenMemberTypeIsClient_ShouldFail() throws Exception {
            mockMvc.perform(post("/projects/{projectId}/applications", projectId)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
        }
    }
}