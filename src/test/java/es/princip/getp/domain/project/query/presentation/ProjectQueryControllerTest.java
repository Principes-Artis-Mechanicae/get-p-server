package es.princip.getp.domain.project.query.presentation;

import es.princip.getp.common.domain.Duration;
import es.princip.getp.common.domain.Hashtag;
import es.princip.getp.common.dto.HashtagsResponse;
import es.princip.getp.common.util.ControllerTest;
import es.princip.getp.domain.project.command.domain.AttachmentFile;
import es.princip.getp.domain.project.command.domain.MeetingType;
import es.princip.getp.domain.project.command.domain.ProjectCategory;
import es.princip.getp.domain.project.command.domain.ProjectStatus;
import es.princip.getp.domain.project.query.dao.ProjectDao;
import es.princip.getp.domain.project.query.dto.AttachmentFilesResponse;
import es.princip.getp.domain.project.query.dto.ProjectCardResponse;
import es.princip.getp.domain.project.query.dto.ProjectClientResponse;
import es.princip.getp.domain.project.query.dto.ProjectDetailResponse;
import es.princip.getp.domain.project.query.presentation.description.ProjectCardResponseDescription;
import es.princip.getp.domain.project.query.presentation.description.ProjectDetailResponseDescription;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static es.princip.getp.domain.client.fixture.AddressFixture.address;
import static es.princip.getp.domain.member.fixture.NicknameFixture.NICKNAME;
import static es.princip.getp.infra.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.infra.docs.PageResponseDescriptor.pageResponseFieldDescriptors;
import static es.princip.getp.infra.docs.PayloadDocumentationHelper.responseFields;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectQueryControllerTest extends ControllerTest {

    @Autowired
    private ProjectDao projectDao;

    @DisplayName("프로젝트 목록 조회")
    @Nested
    class GetProjects {

        final int page = 0;
        final int pageSize = 10;
        final Sort sort = Sort.by(Sort.Order.desc("projectId"));
        final Pageable pageable = PageRequest.of(page, pageSize, sort);

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/projects")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .queryParam("page", String.valueOf(page))
                .queryParam("size", String.valueOf(pageSize))
                .queryParam("sort", "projectId,desc"));
        }

        @Test
        @DisplayName("사용자는 프로젝트 목록을 조회할 수 있다.")
        void getProjects() throws Exception {
            final List<ProjectCardResponse> content = List.of(
                new ProjectCardResponse(
                    1L,
                    "프로젝트 제목",
                    1_000_000L,
                    5L,
                    10L,
                    Duration.of(
                        LocalDate.of(2024, 7, 1),
                        LocalDate.of(2024, 7, 7)
                    ),
                    HashtagsResponse.from(
                        List.of(
                            Hashtag.from("#해시태그1"),
                            Hashtag.from("#해시태그2")
                        )
                    ),
                    "프로젝트 설명",
                    ProjectStatus.APPLYING
                )
            );
            final Page<ProjectCardResponse> response = new PageImpl<>(content, pageable, content.size());
            given(projectDao.findPagedProjectCard(pageable)).willReturn(response);

            perform()
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    responseFields(ProjectCardResponseDescription.description())
                        .and(pageResponseFieldDescriptors())
                ))
                .andDo(print());
        }
    }

    @DisplayName("프로젝트 상세 조회")
    @Nested
    class GetProjectByProjectId {

        private final Long projectId = 1L;

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/projects/{projectId}", projectId)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @Test
        @DisplayName("사용자는 프로젝트의 상세 정보를 조회할 수 있다.")
        void getProjectByProjectId() throws Exception {
            ProjectDetailResponse response = new ProjectDetailResponse(
                projectId,
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
                        Hashtag.from("#해시태그1"),
                        Hashtag.from("#해시태그2")
                    )
                ),
                5L,
                new ProjectClientResponse(
                    1L,
                    NICKNAME,
                    address()
                )
            );
            given(projectDao.findProjectDetailById(projectId)).willReturn(response);

            perform()
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    pathParameters(parameterWithName("projectId").description("프로젝트 ID")),
                    responseFields(ProjectDetailResponseDescription.description())
                ))
                .andDo(print());
        }
    }
}