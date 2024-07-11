package es.princip.getp.domain.people.controller;

import es.princip.getp.domain.member.domain.Member;
import es.princip.getp.domain.people.controller.description.request.CreatePeopleRequestDescription;
import es.princip.getp.domain.people.controller.description.request.UpdatePeopleRequestDescription;
import es.princip.getp.domain.people.controller.description.response.CreatePeopleResponseDescription;
import es.princip.getp.domain.people.controller.description.response.PeopleResponseDescription;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequest;
import es.princip.getp.domain.people.service.PeopleService;
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
import static es.princip.getp.domain.people.fixture.PeopleFixture.*;
import static es.princip.getp.global.support.HeaderDescriptorHelper.authorizationHeaderDescriptor;
import static es.princip.getp.global.support.PayloadDocumentationHelper.responseFields;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({MyPeopleController.class, PeopleErrorCodeController.class})
class MyPeopleControllerTest extends AbstractControllerTest {

    @MockBean
    private PeopleService peopleService;

    @DisplayName("피플은 자신의 정보를 등록할 수 있다.")
    @Nested
    class CreateMyPeople {

        private final CreatePeopleRequest request = createPeopleRequest();

        private ResultActions perform() throws Exception {
            return mockMvc.perform(post("/people/me")
                    .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                    .content(objectMapper.writeValueAsString(request)));
        }

        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        @Test
        public void createMyPeople(PrincipalDetails principalDetails) throws Exception {
            Member member = principalDetails.getMember();
            People people = spy(createPeople(member));
            given(people.getPeopleId()).willReturn(1L);
            given(peopleService.create(member.getMemberId(), request)).willReturn(people);

            perform()
                .andExpect(status().isCreated())
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    requestFields(CreatePeopleRequestDescription.description()),
                    responseFields(CreatePeopleResponseDescription.description())
                ))
                .andDo(print());
        }

        @WithCustomMockUser(memberType = ROLE_CLIENT)
        @Test
        public void createMyPeople_WhenMemberTypeIsClient_ShouldFail() throws Exception {
            expectForbidden(perform());
        }
    }

    @DisplayName("피플은 자신의 정보를 조회할 수 있다.")
    @Nested
    class GetMyPeople {

        private ResultActions perform() throws Exception {
            return mockMvc.perform(get("/people/me")
                    .header("Authorization", "Bearer ${ACCESS_TOKEN}"));
        }

        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        @Test
        void getMyPeople(PrincipalDetails principalDetails) throws Exception {
            Member member = principalDetails.getMember();
            People people = spy(createPeople(member));
            given(people.getPeopleId()).willReturn(1L);
            given(peopleService.getByMemberId(member.getMemberId())).willReturn(people);

            perform()
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    responseFields(PeopleResponseDescription.description())
                ))
                .andDo(print());
        }

        @WithCustomMockUser(memberType = ROLE_CLIENT)
        @Test
        public void getMyPeople_WhenMemberTypeIsClient_ShouldFail() throws Exception {
            expectForbidden(perform());
        }
    }

    @DisplayName("피플은 자신의 정보를 수정할 수 있다.")
    @Nested
    class UpdateMyPeople {

        private final UpdatePeopleRequest request = updatePeopleRequest();

        private ResultActions perform() throws Exception {
            return mockMvc.perform(put("/people/me")
                .header("Authorization", "Bearer ${ACCESS_TOKEN}")
                .content(objectMapper.writeValueAsString(request)));
        }

        @WithCustomMockUser(memberType = ROLE_PEOPLE)
        @Test
        public void updateMyPeople(PrincipalDetails principalDetails) throws Exception {
            Member member = principalDetails.getMember();
            People people = spy(createPeople(member));
            given(people.getPeopleId()).willReturn(1L);
            given(peopleService.update(member.getMemberId(), request)).willReturn(people);

            perform()
                .andExpect(status().isCreated())
                .andDo(restDocs.document(
                    requestHeaders(authorizationHeaderDescriptor()),
                    requestFields(UpdatePeopleRequestDescription.description())
                ))
                .andDo(print());
        }

        @WithCustomMockUser(memberType = ROLE_CLIENT)
        @Test
        public void updateMyPeople_WhenMemberTypeIsClient_ShouldFail() throws Exception {
            expectForbidden(perform());
        }
    }
}