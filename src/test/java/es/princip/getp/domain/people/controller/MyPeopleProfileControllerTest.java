package es.princip.getp.domain.people.controller;

import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.people.controller.description.request.CreatePeopleProfileRequestDescription;
import es.princip.getp.domain.people.controller.description.request.UpdatePeopleProfileRequestDescription;
import es.princip.getp.domain.people.controller.description.response.DetailPeopleProfileResponseDescription;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleProfile;
import es.princip.getp.domain.people.dto.request.CreatePeopleProfileRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleProfileRequest;
import es.princip.getp.domain.people.service.PeopleProfileService;
import es.princip.getp.global.mock.WithCustomMockUser;
import es.princip.getp.global.security.details.PrincipalDetails;
import es.princip.getp.global.support.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import static es.princip.getp.domain.member.domain.MemberType.ROLE_CLIENT;
import static es.princip.getp.domain.member.domain.MemberType.ROLE_PEOPLE;
import static es.princip.getp.domain.people.fixture.PeopleFixture.createPeople;
import static es.princip.getp.domain.people.fixture.PeopleProfileFixture.*;
import static es.princip.getp.global.support.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.global.support.PayloadDocumentationHelper.responseFields;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({MyPeopleProfileController.class})
class MyPeopleProfileControllerTest extends AbstractControllerTest {

    @MockBean
    private PeopleProfileService peopleProfileService;

    private static final String MY_PEOPLE_PROFILE_URI = "/people/me/profile";

    @DisplayName("피플은 자신의 프로필을 등록할 수 있다.")
    @Nested
    class CreateMyPeopleProfile {

        private final CreatePeopleProfileRequest request = createPeopleProfileRequest();

        private ResultActions perform() throws Exception {
            return mockMvc.perform(post(MY_PEOPLE_PROFILE_URI)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .content(objectMapper.writeValueAsString(request)));
        }

        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        @Test
        public void createMyPeopleProfile(PrincipalDetails principalDetails) throws Exception {
            Member member = principalDetails.getMember();
            People people = spy(createPeople(member));
            PeopleProfile profile = createPeopleProfile(request, people);
            given(people.getPeopleId()).willReturn(1L);
            given(peopleProfileService.create(member.getMemberId(), request)).willReturn(profile);

            perform()
                .andExpect(status().isCreated())
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    requestFields(CreatePeopleProfileRequestDescription.description())
                ))
                .andDo(print());
        }

        @WithCustomMockUser(memberType = ROLE_CLIENT)
        @Test
        public void createMyPeopleProfile_WhenMemberTypeIsClient_ShouldFail() throws Exception {
            expectForbidden(perform());
        }
    }

    @DisplayName("피플은 자신의 프로필을 조회할 수 있다.")
    @Nested
    class GetMyPeopleProfile {

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get(MY_PEOPLE_PROFILE_URI)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        @Test
        void getMyPeopleProfile(PrincipalDetails principalDetails) throws Exception {
            Member member = principalDetails.getMember();
            People people = spy(createPeople(member));
            PeopleProfile profile = createPeopleProfile(people);
            given(peopleProfileService.getByMemberId(member.getMemberId())).willReturn(profile);

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

    @DisplayName("피플은 자신의 프로필을 수정할 수 있다.")
    @Nested
    class UpdateMyPeopleProfile {

        private final UpdatePeopleProfileRequest request = updatePeopleProfileRequest();

        private ResultActions perform() throws Exception {
            return mockMvc.perform(put(MY_PEOPLE_PROFILE_URI)
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .content(objectMapper.writeValueAsString(request)));
        }

        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        @Test
        public void updateMyPeopleProfile(PrincipalDetails principalDetails) throws Exception {
            Member member = principalDetails.getMember();
            People people = spy(createPeople(member));
            PeopleProfile profile = createPeopleProfile(request, people);
            given(peopleProfileService.update(member.getMemberId(), request)).willReturn(profile);

            perform()
                .andExpect(status().isCreated())
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    requestFields(UpdatePeopleProfileRequestDescription.description())
                ))
                .andDo(print());
        }

        @WithCustomMockUser(memberType = ROLE_CLIENT)
        @Test
        public void updateMyPeopleProfile_WhenMemberTypeIsClient_ShouldFail() throws Exception {
            expectForbidden(perform());
        }
    }
}