package es.princip.getp.domain.people.query.presentation;

import es.princip.getp.domain.people.command.domain.PeopleType;
import es.princip.getp.domain.people.command.presentation.PeopleErrorCodeController;
import es.princip.getp.domain.people.query.dao.PeopleDao;
import es.princip.getp.domain.people.query.dto.people.CardPeopleResponse;
import es.princip.getp.domain.people.query.dto.people.DetailPeopleResponse;
import es.princip.getp.domain.people.query.dto.people.PeopleMemberResponse;
import es.princip.getp.domain.people.query.dto.people.PublicDetailPeopleResponse;
import es.princip.getp.domain.people.query.dto.peopleProfile.CardPeopleProfileResponse;
import es.princip.getp.domain.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.domain.people.query.dto.peopleProfile.PublicDetailPeopleProfileResponse;
import es.princip.getp.domain.people.query.presentation.description.response.DetailPeopleResponseDescription;
import es.princip.getp.domain.people.query.presentation.description.response.PublicDetailPeopleResponseDescription;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static es.princip.getp.domain.common.fixture.HashtagFixture.hashtagDtos;
import static es.princip.getp.domain.common.fixture.TechStackFixture.techStackDtos;
import static es.princip.getp.domain.member.command.domain.model.MemberType.ROLE_PEOPLE;
import static es.princip.getp.domain.member.fixture.NicknameFixture.NICKNAME;
import static es.princip.getp.domain.member.fixture.ProfileImageFixture.profileImage;
import static es.princip.getp.domain.people.fixture.ActivityAreaFixture.activityArea;
import static es.princip.getp.domain.people.fixture.EducationFixture.education;
import static es.princip.getp.domain.people.fixture.IntroductionFixture.introduction;
import static es.princip.getp.domain.people.fixture.PortfolioFixture.portfolioResponses;
import static es.princip.getp.infra.util.FieldDescriptorHelper.getDescriptor;
import static es.princip.getp.infra.util.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.infra.util.PageResponseDescriptor.pageResponseFieldDescriptors;
import static es.princip.getp.infra.util.PayloadDocumentationHelper.responseFields;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({PeopleQueryController.class, PeopleErrorCodeController.class})
class PeopleQueryControllerTest extends AbstractControllerTest {

    @MockBean
    private PeopleDao peopleDao;

    private PeopleMemberResponse peopleMemberResponse() {
        return new PeopleMemberResponse(
            NICKNAME,
            profileImage(1L).getUri()
        );
    }

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
            Pageable pageable = PageRequest.of(page, size, sort);
            List<CardPeopleResponse> content = List.of(
                new CardPeopleResponse(
                    1L,
                    PeopleType.INDIVIDUAL,
                    peopleMemberResponse(),
                    new CardPeopleProfileResponse(
                        activityArea(),
                        hashtagDtos(),
                        0,
                        0
                    )
                )
            );
            Page<CardPeopleResponse> page = new PageImpl<>(content, pageable, content.size());
            given(peopleDao.findCardPeoplePage(any(Pageable.class))).willReturn(page);

            perform()
                .andExpect(status().isOk())
                .andDo(
                    restDocs.document(
                        queryParameters(
                            parameterWithName("page").description("페이지 번호")
                                .optional().attributes(key("default").value("0")),
                            parameterWithName("size").description("페이지 크기")
                                .optional().attributes(key("default").value("10")),
                            parameterWithName("sort").description("정렬 방식")
                                .optional().attributes(key("default").value("peopleId,desc"))
                        ),
                        responseFields(
                            getDescriptor("content[].peopleId", "피플 ID"),
                            getDescriptor("content[].peopleType", "피플 유형")
                                .attributes(key("format").value("TEAM, INDIVIDUAL")),
                            getDescriptor("content[].member.nickname", "닉네임"),
                            getDescriptor("content[].member.profileImageUri", "프로필 이미지 URI"),
                            getDescriptor("content[].profile.activityArea", "활동 지역"),
                            getDescriptor("content[].profile.hashtags[]", "해시태그"),
                            getDescriptor("content[].profile.completedProjectsCount", "완수한 프로젝트 수"),
                            getDescriptor("content[].profile.interestsCount", "받은 관심 수")
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
                PeopleType.INDIVIDUAL,
                peopleMemberResponse(),
                new PublicDetailPeopleProfileResponse(
                    hashtagDtos(),
                    0,
                    0
                )
            );
            given(peopleDao.findPublicDetailPeopleById(peopleId)).willReturn(Optional.of(response));

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
        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        public void getPeople_WhenUserLogined() throws Exception {
            DetailPeopleResponse response = new DetailPeopleResponse(
                1L,
                PeopleType.INDIVIDUAL,
                peopleMemberResponse(),
                new DetailPeopleProfileResponse(
                    introduction(),
                    activityArea(),
                    techStackDtos(),
                    education(),
                    hashtagDtos(),
                    0,
                    0,
                    portfolioResponses()
                )
            );
            given(peopleDao.findDetailPeopleById(peopleId)).willReturn(Optional.of(response));

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