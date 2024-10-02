package es.princip.getp.api.controller.project.query;

import es.princip.getp.api.controller.project.query.description.GetProjectsQueryParametersDescription;
import es.princip.getp.api.controller.project.query.description.ProjectCardResponseDescription;
import es.princip.getp.api.controller.project.query.description.ProjectDetailResponseDescription;
import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.api.controller.project.query.dto.ProjectDetailResponse;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.project.commission.command.GetProjectCommand;
import es.princip.getp.application.project.commission.port.in.GetProjectQuery;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import es.princip.getp.fixture.member.MemberFixture;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.api.docs.PageResponseDescriptor.pageResponseFieldDescriptors;
import static es.princip.getp.api.docs.PayloadDocumentationHelper.responseFields;
import static es.princip.getp.domain.member.model.MemberType.ROLE_PEOPLE;
import static es.princip.getp.fixture.common.HashtagFixture.hashtagsResponse;
import static es.princip.getp.fixture.project.ProjectFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class ProjectQueryControllerTest extends ControllerTest {

    @Autowired
    private GetProjectQuery getProjectQuery;

    @Nested
    @DisplayName("프로젝트 목록 조회")
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
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    queryParameters(GetProjectsQueryParametersDescription.description(page, pageSize)),
                    responseFields(ProjectCardResponseDescription.description())
                        .and(pageResponseFieldDescriptors())
                ))
                .andDo(print());
        }
    }

    @Nested
    @DisplayName("사용자는 프로젝트 상세 정보를 조회할 수 있다.")
    class GetProjectByProjectId {
        private final Member member = spy(MemberFixture.member(ROLE_PEOPLE));
        private final MemberId memberId = new MemberId(1L);
        private final ProjectId projectId = new ProjectId(1L);

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/projects/{projectId}", projectId.getValue())
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @Test
        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        @DisplayName("사용자는 프로젝트의 상세 정보를 조회할 수 있다.")
        void getProjectByProjectId() throws Exception {
            final ProjectDetailResponse response = ProjectQueryResponseFixture.projectDetailResponse(projectId);
            
            given(member.getId()).willReturn(memberId);
            given(getProjectQuery.getDetailBy(any(Member.class), eq(projectId))).willReturn(response);
            
            perform()
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        pathParameters(parameterWithName("projectId").description("프로젝트 ID")),
                        responseFields(ProjectDetailResponseDescription.description())
                ))
                .andDo(print());
        }
    }
}