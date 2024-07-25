package es.princip.getp.domain.project.command.presentation;

import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.domain.project.command.application.ProjectApplicationService;
import es.princip.getp.domain.project.command.application.command.ApplyProjectCommand;
import es.princip.getp.domain.project.command.presentation.dto.request.ApplyProjectRequest;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.support.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static es.princip.getp.domain.project.fixture.ApplyProjectRequestFixture.applyProjectRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectApplicationController.class)
class ProjectApplicationControllerTest extends AbstractControllerTest {

    @MockBean
    private ProjectCommandMapper projectCommandMapper;

    @MockBean
    private ProjectApplicationService projectApplicationService;

    @BeforeEach
    void setUp() {
        given(projectCommandMapper.mapToCommand(anyLong(), anyLong(), any(ApplyProjectRequest.class)))
            .willReturn(mock(ApplyProjectCommand.class));
    }

    @Nested
    @DisplayName("프로젝트 지원")
    class ApplyForProject {

        private final Long projectId = 1L;
        private final Long applicationId = 1L;
        private final ApplyProjectRequest request = applyProjectRequest();

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        @DisplayName("피플은 프로젝트에 지원할 수 있다.")
        void applyForProject() throws Exception {
            given(projectApplicationService.applyForProject(any(ApplyProjectCommand.class)))
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