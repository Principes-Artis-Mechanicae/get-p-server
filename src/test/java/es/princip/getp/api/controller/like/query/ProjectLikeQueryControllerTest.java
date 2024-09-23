package es.princip.getp.api.controller.like.query;

import es.princip.getp.api.controller.project.query.description.ProjectCardResponseDescription;
import es.princip.getp.api.controller.project.query.dto.ProjectCardResponse;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.like.project.port.in.GetLikedProjectQuery;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.member.model.MemberType;
import es.princip.getp.domain.project.commission.model.ProjectStatus;
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
import static es.princip.getp.fixture.common.HashtagFixture.hashtagsResponse;
import static es.princip.getp.fixture.project.ProjectFixture.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectLikeQueryControllerTest extends ControllerTest {

    @Autowired private GetLikedProjectQuery getLikedProjectQuery;

    @Nested
    @DisplayName("좋아요한 프로젝트 목록 조회")
    class GetLikedProjects {

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/projects/liked")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @Test
        @DisplayName("피플은 좋아요한 프로젝트 목록을 조회할 수 있다.")
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        void getLikedProjects() throws Exception {
            final MemberId memberId = new MemberId(1L);
            final int page = 0;
            final int pageSize = 10;
            final Sort sort = Sort.by(Sort.Order.desc("projectId"));
            final Pageable pageable = PageRequest.of(page, pageSize, sort);
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
            given(getLikedProjectQuery.getPagedCards(memberId, pageable)).willReturn(response);

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
}
