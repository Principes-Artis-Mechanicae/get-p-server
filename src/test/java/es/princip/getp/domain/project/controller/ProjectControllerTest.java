package es.princip.getp.domain.project.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.princip.getp.domain.project.entity.Project;
import es.princip.getp.domain.project.service.ProjectService;
import es.princip.getp.fixture.ProjectFixture;
import es.princip.getp.global.config.SecurityConfig;
import es.princip.getp.global.config.SecurityTestConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProjectController.class)
@Import({SecurityConfig.class, SecurityTestConfig.class})
@ActiveProfiles("test")
@RequiredArgsConstructor
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectService projectService;

    @Nested
    class GetProject {
        @Test
        @DisplayName("사용자는 프로젝트의 상세 정보를 조회할 수 있다.")
        void getProject() throws Exception {
            //given
            Project project = ProjectFixture.createProject();

            given(projectService.getByProjectId(any())).willReturn(
                project
            );

            //when and then
            mockMvc.perform(get("/projects/{projectId}", project.getProjectId())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.projectId").value(project.getProjectId()))
                .andExpect(jsonPath("$.data.title").value(project.getTitle()))
                .andExpect(jsonPath("$.data.payment").value(project.getPayment()))
                .andExpect(jsonPath("$.data.applicationDeadline").value(project.getApplicationDeadline().getValue().toString()))
                .andExpect(jsonPath("$.data.estimatedStartDate").value(project.getEstimatedDuration().getStartDate().toString()))
                .andExpect(jsonPath("$.data.estimatedEndDate").value(project.getEstimatedDuration().getEndDate().toString()))
                .andExpect(jsonPath("$.data.description").value(project.getDescription()))
                .andExpect(jsonPath("$.data.meetingType").value(project.getMeetingType().name()))
                .andExpect(jsonPath("$.data.attachmentUris").isArray())
                .andExpect(jsonPath("$.data.hashtags").isArray())
                //.andExpect(jsonPath("$.data.interestsCount").value(project.getInterestsCount()))
                .andExpect(jsonPath("$.data.client.clientId").value(project.getClient().getClientId()))
                .andExpect(jsonPath("$.data.client.nickname").value(project.getClient().getNickname()))
                .andExpect(jsonPath("$.data.client.profileImageUri").value(project.getClient().getProfileImageUri()))
                .andExpect(jsonPath("$.data.client.address").value(project.getClient().getAddress()));
        }
    }
}