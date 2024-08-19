package es.princip.getp.api.controller.project.query;

import es.princip.getp.api.controller.ControllerTest;
import es.princip.getp.api.controller.project.query.description.AppliedProjectCardResponseDescription;
import es.princip.getp.api.controller.project.query.dto.AppliedProjectCardResponse;
import es.princip.getp.api.docs.PayloadDocumentationHelper;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.common.description.PaginationDescription;
import es.princip.getp.common.domain.Duration;
import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.domain.project.command.domain.ProjectStatus;
import es.princip.getp.domain.project.query.dao.AppliedProjectDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.api.docs.PageResponseDescriptor.pageResponseFieldDescriptors;
import static es.princip.getp.common.fixture.HashtagFixture.hashtagsResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AppliedProjectQueryControllerTest extends ControllerTest {
    @MockBean
    private AppliedProjectDao appliedProjectDao;

    @DisplayName("피플은 자신이 지원한 프로젝트 목록을 조회할 수 있다.")
    @Nested
    class GetMyAppliedProjects {

        private final int page = 0;
        private final int size = 10;
        private final Sort sort = Sort.by(Sort.Order.desc("projectId"));

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/people/me/projects")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .queryParam("page", String.valueOf(page))
                .queryParam("size", String.valueOf(size))
                .queryParam("sort", "projectId,desc"));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        public void getMyAppliedProjects() throws Exception {
            final Pageable pageable = PageRequest.of(page, size, sort);
            final List<AppliedProjectCardResponse> content = List.of(
                new AppliedProjectCardResponse(
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
            final Page<AppliedProjectCardResponse> page = new PageImpl<>(content, pageable, content.size());
            given(appliedProjectDao.findPagedMyAppliedProjects(any(Pageable.class), anyLong()))
                .willReturn(page);

            perform()
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        queryParameters(PaginationDescription.description(this.page, size, "projectId,desc")),
                        PayloadDocumentationHelper.responseFields(AppliedProjectCardResponseDescription.description())
                            .and(pageResponseFieldDescriptors())
                    )
                )
                .andDo(print());
        }
    }
}
