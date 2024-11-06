package es.princip.getp.api.controller.project.query;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.project.apply.dto.response.ProjectApplicantResponse;
import es.princip.getp.application.project.apply.dto.response.ProjectApplicantTeammateResponse;
import es.princip.getp.application.project.apply.port.in.GetApplicantQuery;
import es.princip.getp.application.support.CursorPageable;
import es.princip.getp.application.support.dto.SliceResponse;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.controller.project.query.description.PagedProjectApplicantResponseFields.pagedProjectApplicantResponseFields;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static es.princip.getp.api.docs.PaginationDescription.cursorPaginationParameters;
import static es.princip.getp.api.docs.SliceResponseDescription.sliceResponseDescription;
import static es.princip.getp.domain.member.model.MemberType.ROLE_CLIENT;
import static es.princip.getp.domain.project.apply.model.ProjectApplicationStatus.COMPLETED;
import static es.princip.getp.fixture.member.NicknameFixture.NICKNAME;
import static es.princip.getp.fixture.member.ProfileImageFixture.profileImage;
import static es.princip.getp.fixture.people.EducationFixture.education;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectApplicantQueryControllerTest extends ControllerTest {

    @Autowired private GetApplicantQuery getApplicantQuery;

    private ProjectApplicantResponse projectApplicantResponse(
        final Long id,
        final List<ProjectApplicantTeammateResponse> teammates
    ) {
        return new ProjectApplicantResponse(
            id,
            NICKNAME + id,
            profileImage(new MemberId(id)).getUrl(),
            education(),
            COMPLETED,
            teammates
        );
    }

    private ProjectApplicantTeammateResponse projectApplicantTeammateResponse(final Long id) {
        return new ProjectApplicantTeammateResponse(
            id,
            NICKNAME + id,
            profileImage(new MemberId(id)).getUrl()
        );
    }

    @Test
    @WithCustomMockUser(memberType = ROLE_CLIENT)
    void 의뢰자는_프로젝트_지원자_목록을_조회할_수_있다() throws Exception {
        final var projectId = new ProjectId(1L);
        final var pageSize = 5;
        final var content = List.of(
            projectApplicantResponse(1L, null),
            projectApplicantResponse(2L, null),
            projectApplicantResponse(3L, null),
            projectApplicantResponse(4L, List.of(
                projectApplicantTeammateResponse(5L),
                projectApplicantTeammateResponse(6L)
            )),
            projectApplicantResponse(5L, List.of(
                projectApplicantTeammateResponse(6L),
                projectApplicantTeammateResponse(7L)
            ))
        );
        final var response = SliceResponse.of(
            new SliceImpl<>(content, PageRequest.of(0, pageSize), true),
            "eyJpZCI6IDIwfQ=="
        );
        given(getApplicantQuery.getApplicantsBy(
            any(CursorPageable.class),
            any(Member.class),
            eq(projectId)
        ))
        .willReturn(response);

        mockMvc.perform(get("/projects/{projectId}/applicants", projectId.getValue())
            .header("Authorization", "Bearer ${ACCESS_TOKEN}")
            .queryParam("size", String.valueOf(pageSize))
            .queryParam("cursor", "eyJpZCI6IDEwfQ=="))
            .andExpect(status().isOk())
            .andDo(document("project/get-applicants",
                ResourceSnippetParameters.builder()
                    .tag("의뢰자 프로젝트 관리")
                    .description("의뢰자는 프로젝트 지원자 목록을 조회할 수 있다.")
                    .summary("프로젝트 지원자 목록 조회")
                    .responseSchema(Schema.schema("PagedProjectApplicantResponse")),
                requestHeaders(authorizationHeaderDescription().optional()),
                pathParameters(parameterWithName("projectId").description("프로젝트 ID")),
                queryParameters(cursorPaginationParameters(10)),
                responseFields(pagedProjectApplicantResponseFields())
                    .and(sliceResponseDescription())
            ))
            .andDo(print());
    }
}