package es.princip.getp.domain.people.query.presentation;

import es.princip.getp.domain.people.query.dao.PeopleDao;
import es.princip.getp.domain.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.domain.people.query.presentation.description.response.DetailPeopleProfileResponseDescription;
import es.princip.getp.infra.annotation.WithCustomMockUser;
import es.princip.getp.infra.security.details.PrincipalDetails;
import es.princip.getp.infra.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static es.princip.getp.domain.common.fixture.HashtagFixture.hashtagDtos;
import static es.princip.getp.domain.common.fixture.TechStackFixture.techStackDtos;
import static es.princip.getp.domain.member.domain.model.MemberType.ROLE_CLIENT;
import static es.princip.getp.domain.member.domain.model.MemberType.ROLE_PEOPLE;
import static es.princip.getp.domain.people.fixture.ActivityAreaFixture.activityArea;
import static es.princip.getp.domain.people.fixture.EducationFixture.education;
import static es.princip.getp.domain.people.fixture.IntroductionFixture.introduction;
import static es.princip.getp.domain.people.fixture.PortfolioFixture.portfolioResponses;
import static es.princip.getp.infra.util.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.infra.util.PayloadDocumentationHelper.responseFields;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({MyPeopleProfileQueryController.class})
class MyPeopleProfileQueryControllerTest extends AbstractControllerTest {

    @MockBean
    private PeopleDao peopleDao;

    private static final String MY_PEOPLE_PROFILE_URI = "/people/me/profile";

    @DisplayName("피플은 자신의 프로필을 조회할 수 있다.")
    @Nested
    class GetMyPeopleProfile {

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get(MY_PEOPLE_PROFILE_URI)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        @Test
        public void getMyPeopleProfile(PrincipalDetails principalDetails) throws Exception {
            Long memberId = principalDetails.getMember().getMemberId();
            DetailPeopleProfileResponse response = new DetailPeopleProfileResponse(
                introduction(),
                activityArea(),
                techStackDtos(),
                education(),
                hashtagDtos(),
                0,
                0,
                portfolioResponses()
            );
            given(peopleDao.findDetailPeopleProfileByMemberId(memberId)).willReturn(Optional.of(response));

            perform()
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    responseFields(DetailPeopleProfileResponseDescription.description())
                ))
                .andDo(print());
        }

        @WithCustomMockUser(memberType = ROLE_CLIENT)
        @Test
        public void getMyPeopleProfile_WhenMemberTypeIsClient_ShouldFail() throws Exception {
            expectForbidden(perform());
        }
    }
}