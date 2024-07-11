package es.princip.getp.domain.people.presentation;

import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.people.application.PeopleService;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.dto.response.people.CardPeopleResponse;
import es.princip.getp.domain.people.fixture.PeopleFixture;
import es.princip.getp.domain.people.presentation.description.response.DetailPeopleResponseDescription;
import es.princip.getp.domain.people.presentation.description.response.PublicDetailPeopleResponseDescription;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.support.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.ResultActions;

import static es.princip.getp.domain.member.domain.MemberType.ROLE_PEOPLE;
import static es.princip.getp.domain.member.fixture.MemberFixture.createMember;
import static es.princip.getp.domain.people.fixture.PeopleProfileFixture.createPeopleProfile;
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

@WebMvcTest({PeopleController.class, PeopleErrorCodeController.class})
class PeopleControllerTest extends AbstractControllerTest {

    @MockBean
    private PeopleService peopleService;

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
            Page<CardPeopleResponse> cardPeoplePage = PeopleFixture.createCardPeopleResponsePage(pageable, size);
            given(peopleService.getCardPeoplePage(any(Pageable.class))).willReturn(cardPeoplePage);

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
                            getDescriptor("content[].nickname", "닉네임"),
                            getDescriptor("content[].peopleType", "피플 유형")
                                .attributes(key("format").value("TEAM, INDIVIDUAL")),
                            getDescriptor("content[].profileImageUri", "프로필 이미지 URI"),
                            getDescriptor("content[].profile.activityArea", "활동 지역"),
                            getDescriptor("content[].profile.hashtags[].value", "해시태그"),
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
        private final Long peopleId = 2L;

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/people/{peopleId}", peopleId));
        }

        private ResultActions performWithAccessToken() throws Exception {
            return mockMvc.perform(get("/people/{peopleId}", peopleId)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @BeforeEach
        void setUp() {
            Member member = createMember("test2@gmail.com");
            People people = Mockito.spy(PeopleFixture.createPeople(member));
            given(people.getPeopleId()).willReturn(2L);
            given(people.getProfile()).willReturn(createPeopleProfile(people));
            given(peopleService.getByPeopleId(peopleId)).willReturn(people);
        }

        @Test
        public void getPeople_WhenUserNotLogined() throws Exception {
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