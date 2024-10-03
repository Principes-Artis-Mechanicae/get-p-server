package es.princip.getp.api.controller.project.query;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.controller.project.query.dto.AttachmentFilesResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectClientResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.project.commission.command.GetProjectCommand;
import es.princip.getp.application.project.commission.port.in.GetProjectQuery;
import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.project.commission.model.MeetingType;
import es.princip.getp.domain.project.commission.model.ProjectCategory;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.controller.project.query.description.DetailProjectResponseDescription.detailProjectResponseDescription;
import static es.princip.getp.api.controller.project.query.description.GetProjectsQueryParametersDescription.getProjectsQueryParametersDescription;
import static es.princip.getp.api.controller.project.query.description.PagedCardProjectResponseDescription.pagedCardProjectResponseDescription;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static es.princip.getp.api.docs.PageResponseDescriptor.pageResponseFieldDescriptors;
import static es.princip.getp.fixture.client.AddressFixture.address;
import static es.princip.getp.fixture.common.HashtagFixture.hashtagsResponse;
import static es.princip.getp.fixture.member.NicknameFixture.NICKNAME;
import static es.princip.getp.fixture.project.ProjectFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectQueryControllerTest extends ControllerTest {

    @Autowired
    private GetProjectQuery getProjectQuery;

    @Nested
    class 프로젝트_목록_조회 {

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
        void 사용자는_프로젝트_목록을_조회할_수_있다() throws Exception {
            final List<ProjectCardResponse> content = List.of(
                new ProjectCardResponse(
                    1L,
                    TITLE,
                    PAYMENT,
                    RECRUITMENT_COUNT,
                    5L,
                    10L,
                    Duration.of(
                        LocalDate.of(2024, 7, 1),
                        LocalDate.of(2024, 7, 7)
                    ),
                    hashtagsResponse(),
                    DESCRIPTION,
                    ProjectStatus.APPLYING
                )
            );
            final Page<ProjectCardResponse> response = new PageImpl<>(content, pageable, content.size());
            given(getProjectQuery.getPagedCards(any(GetProjectCommand.class)))
                .willReturn(response);

            perform()
                .andExpect(status().isOk())
                .andDo(document("project/get-projects",
                    ResourceSnippetParameters.builder()
                        .tag("프로젝트")
                        .description("사용자는 프로젝트 목록을 조회할 수 있다.")
                        .summary("프로젝트 목록 조회")
                        .responseSchema(Schema.schema("PagedCardProjectResponse")),
                    requestHeaders(authorizationHeaderDescription().optional()),
                    queryParameters(getProjectsQueryParametersDescription(page, pageSize)),
                    responseFields(pagedCardProjectResponseDescription())
                        .and(pageResponseFieldDescriptors())
                ))
                .andDo(print());
        }
    }

    @Nested
    class 프로젝트_상세_조회 {

        private final ProjectId projectId = new ProjectId(1L);

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/projects/{projectId}", projectId.getValue())
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @Test
        void 사용자는_프로젝트의_상세_정보를_조회할_수_있다() throws Exception {
            final ProjectDetailResponse response = new ProjectDetailResponse(
                projectId.getValue(),
                TITLE,
                PAYMENT,
                RECRUITMENT_COUNT,
                5L,
                Duration.of(
                    LocalDate.of(2024, 7, 1),
                    LocalDate.of(2024, 7, 7)
                ),
                Duration.of(
                    LocalDate.of(2024, 7, 14),
                    LocalDate.of(2024, 7, 21)
                ),
                DESCRIPTION,
                MeetingType.IN_PERSON,
                ProjectCategory.BACKEND,
                ProjectStatus.APPLYING,
                AttachmentFilesResponse.from(
                    List.of(
                        AttachmentFile.from("https://example.com/attachment1"),
                        AttachmentFile.from("https://example.com/attachment2")
                    )
                ),
                hashtagsResponse(),
                5L,
                new ProjectClientResponse(
                    1L,
                    NICKNAME,
                    address()
                )
            );
            given(getProjectQuery.getDetailBy(projectId)).willReturn(response);

            perform()
                .andExpect(status().isOk())
                .andDo(document("project/get-project",
                    ResourceSnippetParameters.builder()
                        .tag("프로젝트")
                        .description("사용자는 프로젝트의 상세 정보를 조회할 수 있다.")
                        .summary("프로젝트 상세 조회")
                        .responseSchema(Schema.schema("DetailProjectResponse")),
                    requestHeaders(authorizationHeaderDescription().optional()),
                    pathParameters(parameterWithName("projectId").description("프로젝트 ID")),
                    responseFields(detailProjectResponseDescription())
                ))
                .andDo(print());
        }
    }
}