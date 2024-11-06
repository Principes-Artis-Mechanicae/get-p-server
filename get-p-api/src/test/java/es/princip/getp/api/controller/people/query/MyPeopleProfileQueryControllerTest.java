package es.princip.getp.api.controller.people.query;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import es.princip.getp.application.people.dto.response.peopleProfile.PeopleProfileDetailResponse;
import es.princip.getp.api.security.annotation.WithCustomMockUser;
import es.princip.getp.application.auth.service.PrincipalDetails;
import es.princip.getp.api.support.ControllerTest;
import es.princip.getp.application.people.port.in.GetMyPeopleQuery;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.member.model.MemberType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static es.princip.getp.api.controller.people.query.description.DetailPeopleProfileResponseDescription.detailPeopleProfileResponseDescription;
import static es.princip.getp.api.docs.HeaderDescriptorHelper.authorizationHeaderDescription;
import static es.princip.getp.fixture.common.HashtagFixture.hashtagsResponse;
import static es.princip.getp.fixture.common.TechStackFixture.techStacksResponse;
import static es.princip.getp.fixture.people.ActivityAreaFixture.activityArea;
import static es.princip.getp.fixture.people.EducationFixture.education;
import static es.princip.getp.fixture.people.IntroductionFixture.introduction;
import static es.princip.getp.fixture.people.PortfolioFixture.portfoliosResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MyPeopleProfileQueryControllerTest extends ControllerTest {

    @Autowired private GetMyPeopleQuery getMyPeopleQuery;

    private static final String MY_PEOPLE_PROFILE_URI = "/people/me/profile";

    @Nested
    class 내_피플_프로필_조회 {

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get(MY_PEOPLE_PROFILE_URI)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @Test
        @WithCustomMockUser(memberType = MemberType.ROLE_PEOPLE)
        public void 피플은_자신의_프로필을_조회할_수_있다(PrincipalDetails principalDetails) throws Exception {
            final MemberId memberId = principalDetails.getMember().getId();
            final PeopleProfileDetailResponse response = new PeopleProfileDetailResponse(
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
                .andDo(document("people/get-my-people-profile",
                    ResourceSnippetParameters.builder()
                        .tag("피플")
                        .description("피플은 자신의 프로필을 조회할 수 있다.")
                        .summary("내 피플 프로필 조회")
                        .responseSchema(Schema.schema("DetailPeopleProfileResponse")),
                    requestHeaders(authorizationHeaderDescription()),
                    responseFields(detailPeopleProfileResponseDescription())
                ))
                .andDo(print());
        }
    }
}