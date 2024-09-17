package es.princip.getp.api.controller.people.query;

import es.princip.getp.api.controller.people.query.description.DetailPeopleProfileResponseDescription;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.api.docs.PayloadDocumentationHelper;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.api.security.details.PrincipalDetails;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.people.port.in.GetMyPeopleQuery;
import es.princip.getp.domain.member.model.MemberId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.domain.member.model.MemberType.ROLE_CLIENT;
import static es.princip.getp.domain.member.model.MemberType.ROLE_PEOPLE;
import static es.princip.getp.fixture.common.HashtagFixture.hashtagsResponse;
import static es.princip.getp.fixture.common.TechStackFixture.techStacksResponse;
import static es.princip.getp.fixture.people.ActivityAreaFixture.activityArea;
import static es.princip.getp.fixture.people.EducationFixture.education;
import static es.princip.getp.fixture.people.IntroductionFixture.introduction;
import static es.princip.getp.fixture.people.PortfolioFixture.portfoliosResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyPeopleProfileQueryControllerTest extends ControllerTest {

    @Autowired private GetMyPeopleQuery getMyPeopleQuery;

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
            final MemberId memberId = principalDetails.getMember().getMemberId();
            final DetailPeopleProfileResponse response = new DetailPeopleProfileResponse(
                introduction(),
                activityArea(),
                education(),
                techStacksResponse(),
                hashtagsResponse(),
                portfoliosResponse()
            );
            given(getMyPeopleQuery.getDetailProfileBy(memberId)).willReturn(response);

            perform()
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    PayloadDocumentationHelper.responseFields(DetailPeopleProfileResponseDescription.description())
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