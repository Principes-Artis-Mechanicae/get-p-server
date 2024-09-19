package es.princip.getp.api.controller.people.query;

import es.princip.getp.api.controller.people.query.description.DetailPeopleResponseDescription;
import es.princip.getp.api.controller.people.query.description.PagedCardPeopleResponseDescription;
import es.princip.getp.api.controller.people.query.description.PublicDetailPeopleResponseDescription;
import es.princip.getp.api.controller.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.CardPeopleProfileResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.PublicDetailPeopleProfileResponse;
import es.princip.getp.api.docs.PaginationDescription;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.people.port.in.GetPeopleQuery;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.people.model.PeopleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.api.docs.PageResponseDescriptor.pageResponseFieldDescriptors;
import static es.princip.getp.api.docs.PayloadDocumentationHelper.responseFields;
import static es.princip.getp.domain.member.model.MemberType.ROLE_CLIENT;
import static es.princip.getp.fixture.common.HashtagFixture.hashtagsResponse;
import static es.princip.getp.fixture.common.TechStackFixture.techStacksResponse;
import static es.princip.getp.fixture.member.NicknameFixture.NICKNAME;
import static es.princip.getp.fixture.member.ProfileImageFixture.profileImage;
import static es.princip.getp.fixture.people.ActivityAreaFixture.activityArea;
import static es.princip.getp.fixture.people.EducationFixture.education;
import static es.princip.getp.fixture.people.IntroductionFixture.introduction;
import static es.princip.getp.fixture.people.PortfolioFixture.portfoliosResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PeopleQueryControllerTest extends ControllerTest {

    @Autowired private GetPeopleQuery getPeopleQuery;

    @Nested
    @DisplayName("사용자는 피플 목록을 조회할 수 있다.")
    class GetCardPeoplePage {

        private final MemberId memberId = new MemberId(1L);

        private final int page = 0;
        private final int size = 3;
        private final Sort sort = Sort.by(Sort.Order.desc("peopleId"));

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/people")
                .queryParam("page", String.valueOf(page))
                .queryParam("size", String.valueOf(size))
                .queryParam("sort", "peopleId,desc"));
        }

        @Test
        public void getCardPeoplePage() throws Exception {
            final Pageable pageable = PageRequest.of(page, size, sort);
            final List<CardPeopleResponse> content = List.of(
                new CardPeopleResponse(
                    1L,
                    NICKNAME,
                    profileImage(memberId).getUrl(),
                    PeopleType.INDIVIDUAL,
                    0,
                    0,
                    new CardPeopleProfileResponse(
                        "소개",
                        activityArea(),
                        hashtagsResponse()
                    )
                )
            );
            final Page<CardPeopleResponse> page = new PageImpl<>(content, pageable, content.size());
            given(getPeopleQuery.getPagedCards(any(Pageable.class))).willReturn(page);

            perform()
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        queryParameters(PaginationDescription.description(this.page, size, "peopleId,desc")),
                        responseFields(PagedCardPeopleResponseDescription.description())
                            .and(pageResponseFieldDescriptors())
                    )
                )
                .andDo(print());
        }
    }

    @Nested
    @DisplayName("사용자는 피플의 상세 정보를 조회할 수 있다.")
    class GetPeople {

        private final MemberId memberId = new MemberId(1L);
        private final PeopleId peopleId = new PeopleId(1L);

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/people/{peopleId}", peopleId.getValue()));
        }

        private ResultActions performWithAccessToken() throws Exception {
            return mockMvc.perform(get("/people/{peopleId}", peopleId.getValue())
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @Test
        public void getPeople_WhenUserNotLogined() throws Exception {
            final PublicDetailPeopleResponse response = new PublicDetailPeopleResponse(
                peopleId.getValue(),
                NICKNAME,
                profileImage(memberId).getUrl(),
                PeopleType.INDIVIDUAL,
                0,
                0,
                new PublicDetailPeopleProfileResponse(
                    hashtagsResponse()
                )
            );
            given(getPeopleQuery.getPublicDetailBy(peopleId)).willReturn(response);

            perform()
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        pathParameters(parameterWithName("peopleId").description("피플 ID")),
                        responseFields(PublicDetailPeopleResponseDescription.description())
                    )
                );
        }

        @Test
        @WithCustomMockUser(memberType = ROLE_CLIENT)
        public void getPeople_WhenUserLogined() throws Exception {
            final DetailPeopleResponse response = new DetailPeopleResponse(
                1L,
                NICKNAME,
                profileImage(memberId).getUrl(),
                PeopleType.INDIVIDUAL,
                0,
                0,
                true,
                new DetailPeopleProfileResponse(
                    introduction(),
                    activityArea(),
                    education(),
                    techStacksResponse(),
                    hashtagsResponse(),
                    portfoliosResponse()
                )
            );
            given(getPeopleQuery.getDetailBy(memberId, peopleId)).willReturn(response);

            performWithAccessToken()
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        pathParameters(parameterWithName("peopleId").description("피플 ID")),
                        responseFields(DetailPeopleResponseDescription.description())
                    )
                );
        }
    }
}