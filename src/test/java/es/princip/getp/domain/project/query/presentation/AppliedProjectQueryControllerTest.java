package es.princip.getp.domain.project.query.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.ResultActions;

import es.princip.getp.domain.common.domain.Duration;
import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.domain.project.command.domain.ProjectStatus;
import es.princip.getp.domain.project.query.dao.ProjectApplicationDao;
import es.princip.getp.domain.project.query.dto.AppliedProjectCardResponse;
import es.princip.getp.domain.project.query.presentation.description.AppliedProjectCardResponseDescription;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.support.AbstractControllerTest;

import static es.princip.getp.domain.common.fixture.HashtagFixture.hashtagsResponse;
import static es.princip.getp.infra.util.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.infra.util.PageResponseDescriptor.pageResponseFieldDescriptors;
import static es.princip.getp.infra.util.PayloadDocumentationHelper.responseFields;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppliedProjectQueryController.class)
public class AppliedProjectQueryControllerTest extends AbstractControllerTest{
    @MockBean
    private ProjectApplicationDao projectApplicationDao;

    @DisplayName("피플은 자신이 지원한 프로젝트 목록을 조회할 수 있다.")
    @Nested
    class GetMyAppliedProjects {

        private final int page = 0;
        private final int size = 10;
        private final Sort sort = Sort.by(Sort.Order.desc("projectId"));

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/people/me/applications")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .queryParam("page", String.valueOf(page))
                .queryParam("size", String.valueOf(size))
                .queryParam("sort", "projectId,desc"));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        public void getMyAppliedProjects() throws Exception {
            Pageable pageable = PageRequest.of(page, size, sort);
            List<AppliedProjectCardResponse> content = List.of(
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
            Page<AppliedProjectCardResponse> page = new PageImpl<>(content, pageable, content.size());
            given(projectApplicationDao.findPagedMyAppliedProjects(any(Pageable.class), anyLong(), anyBoolean()))
                .willReturn(page);

            perform()
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        queryParameters(
                            parameterWithName("page").description("페이지 번호")
                                .optional()
                                .attributes(key("default").value("0")),
                            parameterWithName("size").description("페이지 크기")
                                .optional()
                                .attributes(key("default").value("10")),
                            parameterWithName("sort").description("정렬 방식")
                                .optional()
                                .attributes(key("default").value("projectId,desc")),
                            parameterWithName("cancelled").description("만료된 프로젝트 보기 여부")
                                .optional()
                                .attributes(key("default").value("false"))
                        ),
                        responseFields(AppliedProjectCardResponseDescription.description())
                            .and(pageResponseFieldDescriptors())
                    )
                )
                .andDo(print());
        }
    }
}
