package es.princip.getp.domain.project.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.princip.getp.domain.member.domain.enums.MemberType;
import es.princip.getp.domain.project.dto.request.ApplicateProjectRequest;
import es.princip.getp.domain.project.domain.entity.Project;
import es.princip.getp.domain.project.domain.entity.ProjectApplication;
import es.princip.getp.domain.project.service.ProjectApplicationService;
import es.princip.getp.fixture.ProjectApplicationFixture;
import es.princip.getp.fixture.ProjectFixture;
import es.princip.getp.global.config.SecurityConfig;
import es.princip.getp.global.config.SecurityTestConfig;
import es.princip.getp.global.mock.WithCustomMockUser;
import java.time.LocalDate;
import java.util.List;
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

@WebMvcTest(ProjectApplicationController.class)
@Import({SecurityConfig.class, SecurityTestConfig.class})
@ActiveProfiles("test")
@RequiredArgsConstructor
class ProjectApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectApplicationService projectApplicationService;

    @Nested
    @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
    class ApplicateProject {
        @Test
        @DisplayName("피플은 프로젝트 지원을 할 수 있다.")
        void applicateProject() throws Exception {
            //given
            ApplicateProjectRequest request = ProjectApplicationFixture.createApplicateProjectRequest();
            LocalDate expectedStartDate = request.expectedStartDate();
            LocalDate expectedEndDate = request.expectedEndDate();
            String description = request.description();
            List<String> attachmentUris = request.attachmentUris();
            Project project = ProjectFixture.createProject();

            given(projectApplicationService.create(any(), any(), any())).willReturn(
                ProjectApplication.builder()
                    .expectedStartDate(expectedStartDate)
                    .expectedEndDate(expectedEndDate)
                    .description(description)
                    .attachmentUris(attachmentUris)
                    .project(project)
                    .build()
            );

            //when and then
            mockMvc.perform(post("/projects/{projectId}/applicants", project.getProjectId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.expectedStartDate").value(expectedStartDate.toString()))
                .andExpect(jsonPath("$.data.expectedEndDate").value(expectedEndDate.toString()))
                .andExpect(jsonPath("$.data.description").value(description))
                .andExpect(jsonPath("$.data.attachmentUris").isArray())
                .andExpect(jsonPath("$.data.project.projectId").value(project.getProjectId()))
                .andExpect(jsonPath("$.data.project.client.clientId").value(project.getClient().getClientId()));
        }
    }
}