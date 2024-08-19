package es.princip.getp.api.controller.project.query;

import es.princip.getp.api.controller.ControllerTest;
import es.princip.getp.api.controller.PageResponse;
import es.princip.getp.api.controller.people.query.description.PagedDetailPeopleResponseDescription;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.api.controller.project.query.description.GetApplicantsByProjectIdQueryParameterDescription;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.domain.member.command.domain.model.Member;
import es.princip.getp.domain.member.command.domain.model.MemberType;
import es.princip.getp.domain.people.command.domain.PeopleType;
import es.princip.getp.domain.project.query.application.ProjectApplicantService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.api.docs.PageResponseDescriptor.pageResponseFieldDescriptors;
import static es.princip.getp.api.docs.PayloadDocumentationHelper.responseFields;
import static es.princip.getp.common.fixture.HashtagFixture.hashtagsResponse;
import static es.princip.getp.common.fixture.TechStackFixture.techStacksResponse;
import static es.princip.getp.domain.member.fixture.NicknameFixture.NICKNAME;
import static es.princip.getp.domain.member.fixture.ProfileImageFixture.profileImage;
import static es.princip.getp.domain.people.fixture.ActivityAreaFixture.activityArea;
import static es.princip.getp.domain.people.fixture.EducationFixture.education;
import static es.princip.getp.domain.people.fixture.IntroductionFixture.introduction;
import static es.princip.getp.domain.people.fixture.PortfolioFixture.portfoliosResponse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.data.domain.Sort.Order.desc;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectApplicantQueryControllerTest extends ControllerTest {

    @Autowired
    private ProjectApplicantService projectApplicantService;

    @Nested
    @DisplayName("프로젝트 지원자 목록 조회")
    class GetApplicantsByProjectId {

        private final Long projectId = 1L;
        private final int page = 0;
        private final int size = 10;
        private final Sort sort = Sort.by(desc("peopleId"));

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/projects/{projectId}/applicants", projectId)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .queryParam("page", String.valueOf(page))
                .queryParam("size", String.valueOf(size))
                .queryParam("sort", "peopleId,desc"));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_CLIENT)
        @DisplayName("의뢰자는 자신이 의뢰한 프로젝트의 지원자 목록을 조회할 수 있다.")
        void getApplicantsByProjectId(final PrincipalDetails principalDetails) throws Exception {
            final Member member = principalDetails.getMember();
            final Pageable pageable = PageRequest.of(page, size, sort);
            final List<DetailPeopleResponse> content = List.of(
                new DetailPeopleResponse(
                    1L,
                    NICKNAME,
                    profileImage(1L).getUri(),
                    PeopleType.INDIVIDUAL,
                    0,
                    0,
                    new DetailPeopleProfileResponse(
                        introduction(),
                        activityArea(),
                        education(),
                        techStacksResponse(),
                        hashtagsResponse(),
                        portfoliosResponse()
                    )
                )
            );
            final PageResponse<DetailPeopleResponse> page =
                PageResponse.from(new PageImpl<>(content, pageable, content.size()));
            given(projectApplicantService.getApplicants(eq(projectId), eq(member), eq(pageable)))
                .willReturn(page);

            perform()
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        pathParameters(parameterWithName("projectId").description("프로젝트 ID")),
                        RequestDocumentation.queryParameters(GetApplicantsByProjectIdQueryParameterDescription.description()),
                        responseFields(PagedDetailPeopleResponseDescription.description())
                            .and(pageResponseFieldDescriptors())
                    )
                )
                .andDo(print());
        }
    }
}