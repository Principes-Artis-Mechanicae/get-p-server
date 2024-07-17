package es.princip.getp.domain.project.query.presentation;

import es.princip.getp.domain.common.domain.Duration;
import es.princip.getp.domain.project.command.domain.MeetingType;
import es.princip.getp.domain.project.command.domain.ProjectCategory;
import es.princip.getp.domain.project.command.domain.ProjectStatus;
import es.princip.getp.domain.project.query.dao.ProjectDao;
import es.princip.getp.domain.project.query.dto.DetailProjectResponse;
import es.princip.getp.domain.project.query.dto.ProjectClientResponse;
import es.princip.getp.infra.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static es.princip.getp.domain.client.fixture.AddressFixture.address;
import static es.princip.getp.domain.common.fixture.HashtagFixture.hashtagDtos;
import static es.princip.getp.domain.member.fixture.NicknameFixture.NICKNAME;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectQueryController.class)
class ProjectQueryControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectDao projectDao;

    @DisplayName("프로젝트 상세 조회")
    @Nested
    class GetProject {

        private final Long peopleId = 1L;

        @Test
        @DisplayName("사용자는 프로젝트의 상세 정보를 조회할 수 있다.")
        void getProject() throws Exception {
            DetailProjectResponse response = new DetailProjectResponse(
                peopleId,
                "프로젝트 제목",
                1_000_000L,
                Duration.of(
                    LocalDate.of(2024, 7, 1),
                    LocalDate.of(2024, 7, 7)
                ),
                Duration.of(
                    LocalDate.of(2024, 7, 14),
                    LocalDate.of(2024, 7, 21)
                ),
                "프로젝트 설명",
                MeetingType.IN_PERSON,
                ProjectCategory.BACKEND,
                ProjectStatus.APPLYING,
                List.of(
                    "https://example.com/attachment1",
                    "https://example.com/attachment2"
                ),
                hashtagDtos(),
                5L,
                new ProjectClientResponse(
                    1L,
                    NICKNAME,
                    address()
                )
            );
            given(projectDao.findDetailProjectById(peopleId)).willReturn(Optional.of(response));

            mockMvc.perform(get("/projects/{projectId}", peopleId))
                .andExpect(status().isOk());
        }
    }
}