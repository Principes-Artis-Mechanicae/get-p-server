package es.princip.getp.api.controller.people.query;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.people.dto.command.GetPeopleCommand;
import es.princip.getp.application.people.dto.response.people.CardPeopleResponse;
import es.princip.getp.application.people.dto.response.people.PeopleDetailResponse;
import es.princip.getp.application.people.dto.response.peopleProfile.CardPeopleProfileResponse;
import es.princip.getp.application.people.dto.response.peopleProfile.PeopleProfileDetailResponse;
import es.princip.getp.application.people.port.in.GetPeopleQuery;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.fixture.member.MemberFixture;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.controller.common.fixture.HashtagDtoFixture.hashtagsResponse;
import static es.princip.getp.api.controller.common.fixture.TechStackDtoFixture.techStacksResponse;
import static es.princip.getp.api.controller.people.fixture.PortfolioFixture.portfoliosResponse;
import static es.princip.getp.api.controller.people.query.description.DetailPeopleResponseDescription.detailPeopleResponseDescription;
import static es.princip.getp.api.controller.people.query.description.GetCardPeoplePageQueryParametersDescription.getCardPeoplePageQueryParametersDescription;
import static es.princip.getp.api.controller.people.query.description.PagedCardPeopleResponseDescription.pagedCardPeopleResponseDescription;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static es.princip.getp.api.docs.PageResponseDescriptor.pageResponseFieldDescriptors;
import static es.princip.getp.domain.member.model.MemberType.ROLE_CLIENT;
import static es.princip.getp.fixture.member.NicknameFixture.NICKNAME;
import static es.princip.getp.fixture.member.ProfileImageFixture.profileImage;
import static es.princip.getp.fixture.people.ActivityAreaFixture.activityArea;
import static es.princip.getp.fixture.people.EducationFixture.education;
import static es.princip.getp.fixture.people.IntroductionFixture.INTRODUCTION;
import static es.princip.getp.fixture.people.IntroductionFixture.introduction;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PeopleQueryControllerTest extends ControllerTest {

    @Autowired private GetPeopleQuery getPeopleQuery;

    @Nested
    class 피플_목록_조회 {

        private final MemberId memberId = new MemberId(1L);

        private final int page = 0;
        private final int size = 3;
        private final Sort sort = Sort.by(Sort.Order.desc("peopleId"));
        private final Pageable pageable = PageRequest.of(page, size, sort);

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/people")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .queryParam("page", String.valueOf(page))
                .queryParam("size", String.valueOf(size))
                .queryParam("sort", "peopleId,desc"));
        }

        @Test
        public void 사용자는_피플_목록을_조회할_수_있다() throws Exception {
            final List<CardPeopleResponse> content = List.of(
                new CardPeopleResponse(
                    1L,
                    NICKNAME,
                    profileImage(memberId).getUrl(),
                    3,
                    5,
                    new CardPeopleProfileResponse(
                        INTRODUCTION,
                        activityArea(),
                        hashtagsResponse()
                    )
                )
            );
            final Page<CardPeopleResponse> response = new PageImpl<>(content, pageable, content.size());
            given(getPeopleQuery.getPagedCards(any(GetPeopleCommand.class))).willReturn(response);

            perform()
                .andExpect(status().isOk())
                .andDo(document("people/get-people-list",
                    ResourceSnippetParameters.builder()
                        .tag("피플")
                        .description("사용자는 피플 목록을 조회할 수 있다.")
                        .summary("피플 목록 조회")
                        .responseSchema(Schema.schema("PagedCardPeopleResponse")),
                    requestHeaders(authorizationHeaderDescription().optional()),
                    queryParameters(getCardPeoplePageQueryParametersDescription(page, size)),
                    responseFields(pagedCardPeopleResponseDescription())
                        .and(pageResponseFieldDescriptors())
                ))
                .andDo(print());
        }
    }

    @Nested
    class 피플_상세_정보_조회 {

        private final Member member = spy(MemberFixture.member(ROLE_CLIENT));
        private final MemberId memberId = new MemberId(1L);
        private final PeopleId peopleId = new PeopleId(1L);

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/people/{peopleId}", peopleId.getValue())
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @Test
        @WithCustomMockUser(memberType = ROLE_CLIENT)
        public void 사용자는_피플_상세_정보를_조회할_수있다() throws Exception {
            final PeopleDetailResponse response = new PeopleDetailResponse(
                1L,
                NICKNAME,
                profileImage(memberId).getUrl(),
                0,
                0,
                true,
                new PeopleProfileDetailResponse(
                    introduction(),
                    activityArea(),
                    education(),
                    techStacksResponse(),
                    hashtagsResponse(),
                    portfoliosResponse()
                )
            );

            given(member.getId()).willReturn(memberId);
            given(getPeopleQuery.getDetailBy(any(Member.class), any(PeopleId.class))).willReturn(response);

            perform()
                .andExpect(status().isOk())
                .andDo(document("people/get-people",
                    ResourceSnippetParameters.builder()
                        .tag("피플")
                        .description("사용자는 피플 상세 정보를 조회할 수 있다.")
                        .summary("피플 상세 정보 조회")
                        .responseSchema(Schema.schema("DetailPeopleResponse")),
                    requestHeaders(authorizationHeaderDescription().optional()),
                    pathParameters(parameterWithName("peopleId").description("피플 ID")),
                    responseFields(detailPeopleResponseDescription())
                ));
        }
    }
}