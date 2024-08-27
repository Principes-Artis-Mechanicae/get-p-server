package es.princip.getp.api.controller.people.query;

import es.princip.getp.api.controller.ControllerTest;
import es.princip.getp.api.controller.people.query.description.DetailPeopleResponseDescription;
import es.princip.getp.api.controller.people.query.description.PublicDetailPeopleResponseDescription;
import es.princip.getp.api.controller.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.CardPeopleProfileResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.PublicDetailPeopleProfileResponse;
import es.princip.getp.api.docs.PayloadDocumentationHelper;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.docs.PaginationDescription;
import es.princip.getp.domain.people.command.domain.PeopleType;
import es.princip.getp.domain.people.query.dao.PeopleDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static es.princip.getp.api.docs.FieldDescriptorHelper.getDescriptor;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.api.docs.PageResponseDescriptor.pageResponseFieldDescriptors;
import static es.princip.getp.api.docs.PayloadDocumentationHelper.responseFields;
import static es.princip.getp.fixture.common.HashtagFixture.hashtagsResponse;
import static es.princip.getp.fixture.common.TechStackFixture.techStacksResponse;
import static es.princip.getp.domain.member.model.MemberType.ROLE_PEOPLE;
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
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PeopleQueryControllerTest extends ControllerTest {

    @Autowired
    private PeopleDao peopleDao;

    @DisplayName("사용자는 피플 목록을 조회할 수 있다.")
    @Nested
    class GetCardPeoplePage {

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
                    profileImage(1L).getUrl(),
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
            given(peopleDao.findCardPeoplePage(any(Pageable.class))).willReturn(page);

            perform()
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        queryParameters(PaginationDescription.description(this.page, size, "peopleId,desc")),
                        responseFields(
                            getDescriptor("content[].peopleId", "피플 ID"),
                            getDescriptor("content[].peopleType", "피플 유형")
                                .attributes(key("format").value("TEAM, INDIVIDUAL")),
                            getDescriptor("content[].nickname", "닉네임"),
                            getDescriptor("content[].profileImageUri", "프로필 이미지 URI"),
                            getDescriptor("content[].completedProjectsCount", "완수한 프로젝트 수"),
                            getDescriptor("content[].likesCount", "받은 좋아요 수"),
                            getDescriptor("content[].profile.introduction", "소개"),
                            getDescriptor("content[].profile.activityArea", "활동 지역"),
                            getDescriptor("content[].profile.hashtags[]", "해시태그")
                        ).and(pageResponseFieldDescriptors())
                    )
                )
                .andDo(print());
        }
    }

    @DisplayName("사용자는 피플의 상세 정보를 조회할 수 있다.")
    @Nested
    class GetPeople {

        private final Long peopleId = 1L;

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/people/{peopleId}", peopleId));
        }

        private ResultActions performWithAccessToken() throws Exception {
            return mockMvc.perform(get("/people/{peopleId}", peopleId)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @Test
        public void getPeople_WhenUserNotLogined() throws Exception {
            PublicDetailPeopleResponse response = new PublicDetailPeopleResponse(
                peopleId,
                NICKNAME,
                profileImage(1L).getUrl(),
                PeopleType.INDIVIDUAL,
                0,
                0,
                new PublicDetailPeopleProfileResponse(
                    hashtagsResponse()
                )
            );
            given(peopleDao.findPublicDetailPeopleById(peopleId)).willReturn(response);

            perform()
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        pathParameters(parameterWithName("peopleId").description("피플 ID")),
                        PayloadDocumentationHelper.responseFields(PublicDetailPeopleResponseDescription.description())
                    )
                );
        }

        @Test
        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        public void getPeople_WhenUserLogined() throws Exception {
            DetailPeopleResponse response = new DetailPeopleResponse(
                1L,
                NICKNAME,
                profileImage(1L).getUrl(),
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
            );
            given(peopleDao.findDetailPeopleById(peopleId)).willReturn(response);

            performWithAccessToken()
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        requestHeaders(authorizationHeaderDescriptor()),
                        pathParameters(parameterWithName("peopleId").description("피플 ID")),
                        PayloadDocumentationHelper.responseFields(DetailPeopleResponseDescription.description())
                    )
                );
        }
    }
}