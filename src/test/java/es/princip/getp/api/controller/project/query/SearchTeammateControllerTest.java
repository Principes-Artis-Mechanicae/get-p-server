package es.princip.getp.api.controller.project.query;

import es.princip.getp.api.controller.project.query.description.SearchTeammateQueryParametersDescription;
import es.princip.getp.api.controller.project.query.description.SearchTeammateResponseDescription;
import es.princip.getp.api.controller.project.query.dto.SearchTeammateResponse;
import es.princip.getp.api.docs.SliceResponseDescription;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.api.support.dto.SliceResponse;
import es.princip.getp.application.people.command.SearchTeammateCommand;
import es.princip.getp.application.project.apply.port.in.SearchTeammateQuery;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.api.docs.PayloadDocumentationHelper.responseFields;
import static es.princip.getp.fixture.member.NicknameFixture.NICKNAME;
import static es.princip.getp.fixture.member.ProfileImageFixture.profileImage;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("프로젝트 팀원 검색")
class SearchTeammateControllerTest extends ControllerTest {

    @Autowired private SearchTeammateQuery searchTeammateQuery;

    private final ProjectId projectId = new ProjectId(1L);
    private final int pageSize = 5;

    private ResultActions perform() throws Exception {
        return mockMvc.perform(get("/projects/{projectId}/teammates", projectId.getValue())
            .header("Authorization", "Bearer ${ACCESS_TOKEN}")
            .queryParam("size", String.valueOf(pageSize))
            .queryParam("nickname", NICKNAME)
            .queryParam("cursor", "eyJpZCI6IDEwfQ=="));
    }

    @Test
    @DisplayName("피플은 프로젝트 지원 시 팀원을 검색할 수 있다.")
    void searchTeammates() throws Exception {
        final List<SearchTeammateResponse> content = LongStream.iterate(pageSize * 2, i -> i - 1)
            .limit(pageSize)
            .mapToObj(i -> new SearchTeammateResponse(
                i,
                NICKNAME + i,
                profileImage(new MemberId(i)).getUrl()
            ))
            .toList();
        final SliceResponse<SearchTeammateResponse> response = SliceResponse.of(
            new SliceImpl<>(content, PageRequest.of(0, pageSize), true),
            "eyJpZCI6IDIwfQ=="
        );
        given(searchTeammateQuery.search(any(SearchTeammateCommand.class)))
            .willReturn(response);

        perform()
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                requestHeaders(authorizationHeaderDescriptor()),
                pathParameters(parameterWithName("projectId").description("프로젝트 ID")),
                queryParameters(SearchTeammateQueryParametersDescription.description(pageSize)),
                responseFields(SearchTeammateResponseDescription.description())
                    .and(SliceResponseDescription.description())
            ))
            .andDo(print());
    }
}