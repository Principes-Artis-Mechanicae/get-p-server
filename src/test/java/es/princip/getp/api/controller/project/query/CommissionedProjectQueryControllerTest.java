package es.princip.getp.api.controller.project.query;

import es.princip.getp.api.controller.ControllerTest;
import es.princip.getp.api.controller.project.query.description.GetMyCommissionedProjectQueryParameterDescription;
import es.princip.getp.api.controller.project.query.description.ProjectCardResponseDescription;
import es.princip.getp.api.controller.project.query.dto.CommissionedProjectCardResponse;
import es.princip.getp.api.docs.PayloadDocumentationHelper;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.application.project.commission.port.in.GetCommissionedProjectQuery;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.api.docs.PageResponseDescriptor.pageResponseFieldDescriptors;
import static es.princip.getp.fixture.common.HashtagFixture.hashtagsResponse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.data.domain.Sort.Order.desc;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommissionedProjectQueryControllerTest extends ControllerTest {

    @Autowired
    private GetCommissionedProjectQuery getCommissionedProjectQuery;

    @Nested
    @DisplayName("의뢰한 프로젝트 목록 조회")
    class GetMyCommissionedProjects {

        private final int page = 0;
        private final int size = 10;
        private final Sort sort = Sort.by(desc("projectId"));

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/client/me/projects")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .queryParam("page", String.valueOf(page))
                .queryParam("size", String.valueOf(size))
                .queryParam("sort", "projectId,desc"));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @DisplayName("의뢰자는 자신이 의뢰한 프로젝트 목록을 조회할 수 있다.")
        public void getMyCommissionedProjects() throws Exception {
            final Pageable pageable = PageRequest.of(page, size, sort);
            final List<CommissionedProjectCardResponse> content = List.of(
                new CommissionedProjectCardResponse(
                    1L,
                    "프로젝트 제목",
                    1_000_000L,
                    5L,
                    30L,
                    Duration.of(
                        LocalDate.of(2024, 7, 1),
                        LocalDate.of(2024, 7, 31)
                    ),
                    hashtagsResponse(),
                    "프로젝트 설명",
                    ProjectStatus.APPLYING
                )
            );
            final Page<CommissionedProjectCardResponse> page = new PageImpl<>(content, pageable, content.size());
            given(getCommissionedProjectQuery.getPagedCards(anyLong(), anyBoolean(), any(Pageable.class)))
                .willReturn(page);

            perform()
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        RequestDocumentation.queryParameters(GetMyCommissionedProjectQueryParameterDescription.description()),
                        PayloadDocumentationHelper.responseFields(ProjectCardResponseDescription.description())
                            .and(pageResponseFieldDescriptors())
                    )
                )
                .andDo(print());
        }
    }
}