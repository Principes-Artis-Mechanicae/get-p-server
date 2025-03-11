package es.princip.getp.api.controller.project.query;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.people.dto.command.SearchTeammateCommand;
import es.princip.getp.application.project.apply.dto.response.SearchTeammateResponse;
import es.princip.getp.application.project.apply.port.in.SearchTeammateQuery;
import es.princip.getp.application.support.dto.SliceResponse;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.stream.LongStream;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.controller.project.query.description.SearchTeammateQueryParametersDescription.searchTeammateQueryParametersDescription;
import static es.princip.getp.api.controller.project.query.description.SearchTeammateResponseDescription.searchTeammateResponseDescription;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static es.princip.getp.api.docs.SliceResponseDescription.sliceResponseDescription;
import static es.princip.getp.fixture.member.NicknameFixture.NICKNAME;
import static es.princip.getp.fixture.member.ProfileImageFixture.profileImage;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SearchTeammateControllerTest extends ControllerTest {

    @Autowired private SearchTeammateQuery searchTeammateQuery;

    @Nested
    class 프로젝트_팀원_검색 {
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
        void 피플은_프로젝트_지원_시_팀원을_검색할_수_있다() throws Exception {
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
                .andDo(document("project/search-teammates",
                    ResourceSnippetParameters.builder()
                        .tag("프로젝트 지원")
                        .description("피플은 프로젝트 지원 시 팀원을 검색할 수 있다.")
                        .summary("프로젝트 팀원 검색")
                        .responseSchema(Schema.schema("SearchTeammateResponse")),
                    requestHeaders(authorizationHeaderDescription().optional()),
                    pathParameters(parameterWithName("projectId").description("프로젝트 ID")),
                    queryParameters(searchTeammateQueryParametersDescription(pageSize)),
                    responseFields(searchTeammateResponseDescription())
                        .and(sliceResponseDescription())
                ))
                .andDo(print());
        }
    }
}