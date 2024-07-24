package es.princip.getp.domain.project.query.presentation;

import es.princip.getp.domain.common.domain.Duration;
import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.common.dto.HashtagsResponse;
import es.princip.getp.domain.project.command.domain.AttachmentFile;
import es.princip.getp.domain.project.command.domain.MeetingType;
import es.princip.getp.domain.project.command.domain.ProjectCategory;
import es.princip.getp.domain.project.command.domain.ProjectStatus;
import es.princip.getp.domain.project.query.dao.ProjectDao;
import es.princip.getp.domain.project.query.dto.AttachmentFilesResponse;
import es.princip.getp.domain.project.query.dto.ProjectClientResponse;
import es.princip.getp.domain.project.query.dto.ProjectDetailResponse;
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

import static es.princip.getp.domain.client.fixture.AddressFixture.address;
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
            ProjectDetailResponse response = new ProjectDetailResponse(
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
                AttachmentFilesResponse.from(
                    List.of(
                        AttachmentFile.from("https://example.com/attachment1"),
                        AttachmentFile.from("https://example.com/attachment2")
                    )
                ),
                HashtagsResponse.from(
                    List.of(
                        Hashtag.of("#해시태그1"),
                        Hashtag.of("#해시태그2")
                    )
                ),
                5L,
                new ProjectClientResponse(
                    1L,
                    NICKNAME,
                    address()
                )
            );
            given(projectDao.findProjectDetailById(peopleId)).willReturn(response);

            mockMvc.perform(get("/projects/{projectId}", peopleId))
                .andExpect(status().isOk());
        }
    }
}